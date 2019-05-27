## Gate
ブログや口コミなどの投稿コンテンツなどに対して、投稿者のみの編集ボタンを表示したりコンテンツ更新を許可したりする、管理画面のアクセス許可などを制御する処理を定義する

認証処理と一緒にすると煩雑になったりクラスの責務が大きくなったりしがちであり、切り出すことで、コントローラやミドルウェア、Bladeテンプレートなどで利用する

・サービスプロバイダに認可処理の登録
（App\Providers\AuthServiceProvider.php参照）

```php
public function boot(GateContract $gate)
{
    $this->registerPolicies();
    $gate->define('user-access', function (User $user, $id) {
        return intval($user->getAuthIdentifier()) === intval($id);
    });
}
```

・認可処理の使用
（App\Http\Controllers\User\RetrieveAction.php参照）

allowsでなく、deniesにすると認可されていない場合に処理になる
```php
public function __invoke(string $id)
{
    if ($this->gate->allows('user-access', $id)) {
        // 実行が許可される場合に実行
    }
}
```

---
※認可処理を一つのクラスで実装する場合
・認可処理を実装
（App\Gate\UserAccess.phpを参照）

```php
public function __invoke(User $user, string $id): bool
{
    return intval($user->getAuthIdentifier()) === intval($id);
}
```

・サービスプロバイダに認可処理を登録
（App\Providers\AuthServiceProvider.php参照）
```php
public function boot(GateContract $gate)
{
    $this->registerPolicies();
    $gate->define('user-access', new UserAccess());
}
```
---

認可処理を実行する前に動作させたい処理があれば、beforeメソッドを実装する
（App\Providers\AuthServiceProvider.php参照）
```php
※bootメソッド
    $gate->before(function ($user, $ability) use ($logger) {
    $logger->info($ability, [
            'user_id' => $user->getAuthIdentifier()
        ]);
    });
```