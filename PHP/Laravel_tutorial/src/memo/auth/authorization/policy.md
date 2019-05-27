## ポリシー

リソースに対する認可処理をまとめて記述する仕組み

特定のEloquentモデルと紐づいているわけではないが、コントローラの処理と関連するEloquentモデルを型宣言として、メソッドが記述されたひな形が作成できる  
コマンド例(--modelで対応するEloquentモデルを指定)  
`php artisan make:policy ContentPolicy --model=Content`

### Eloquentモデルを利用したポリシー
作成したクラスとEloquentモデルを対応させるには、App\Providers\AuthServiceProviderのpoliciesプロパティに記述する
```php
    protected $policies = [
        \App\Content::class => \App\Policies\ContentPolicy::class
    ];
```
ここで登録したポリシークラスは、Illuminate\Contracts\Auth\Authenticatableインタフェースを実装したクラスのインスタンスを経由して利用できる
```php
public function __invoke(string $id)
{
    // 認可処理実行例
    $content = Content::find((int) $id);
    /** @var User $user */
    $user = $this->authManager->guard()->user();
    if ($user->can('update', $content)) {
        // 実行可能な場合処理される
    }
}
```

### Eloqentモデルを利用しないポリシー
PHPのビルトインクラスstdClassをキーに記述した例
```php
    protected $policies = [
        \stdClass::class => ContentPolicy::class
    ];
```

PHPのビルトインクラスを使用したポリシークラスの実装例
```php
    public function edit(Authenticatable $authenticatable, stdClass $class): bool
    {
        if (property_exists($class, 'id')) {
            return intval($authenticatable->getAuthIdentifier()) === intval($class->id);
        }

        return false;
    }
```

ポリシークラスの利用例
```php
    public function __invoke(string $id)
    {
        $class = new \stdClass();
        $class->id = 1;
        $this->gate->forUser(
            $this->authManager->guard)->user()
        )->allows('edit', $class);
    }
```

### Bladeテンプレートによる認可処理
認可処理をBladeテンプレートに記述する例
```php
@can('edit', $content)
    // コンテンツ編集のためのボタンなどを表示
@elsecan('create', App\Content::class)
    // コンテンツ作成のための描画行われる
@endcan
```

テンプレート描画でいくつかのロジックが必要になる場合は、View Composerでロジックと描画を分離できる

・Bladeテンプレート例
```php
<html>
<head>
    <title>Laravel Gate Example</title>
</head>
<body>
    <div class="container">
    sample content
    @yield('authorized')
    </div>
</body>
</html>
```

・ビューコンポーザ実装
（App\Composers\PolicyComposer.php参照）
認可の可否で@yieldに入る値が変わる  
同様にテンプレート描画を指定することで表示内容を大きく変更できる  
（$view->getFactory('テンプレート名')->render()）

・サービスプロバイダにビューコンポーザが作用するテンプレートを指定
```php
namespace App\Providers;

use App\Composers\PolicyComposer;
use Illuminate\Support\ServiceProvider;
use Illuminate\View\Factory;

class AppServiceProvider extends ServiceProvider
{
    public function boot(Factory $factory)
    {
        $factory->composer('PolicyComposerを利用したいテンプレート名', PolicyComposer::class);
    }
}
```