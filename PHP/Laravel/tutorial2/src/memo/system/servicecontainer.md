## サービスコンテナ

Laravelにおけるインスタンス管理の役割を担っているのがサービスコンテナ  
サービスコンテナにインスタンス管理を任せておけば、ビジネスロジックを用いるときに、サービスコンテナに対してインスタンスを要求するだけでいい

### バインド
サービスコンテナにインスタンス管理を任せるためには、あらかじめインスタンス生成方法を知らせる必要がある

サービスに対してインスタンスの生成方法を登録する処理を`バインド`と呼ぶ  
指定されたインスタンスをサービスコンテナが生成して返すことを`解決する(resolve)`と呼ぶ

バインドの定義場所は、アプリケーション内で解決するクラス名のバインド処理はapp\ProvidersフォルダにServiceProviderクラスを生成して定義するとよい（もしくは既存のAppServiceProviderに定義）

通常、バインド処理はregisterメソッドに記載する。bootメソッドが実行されるときはほかの機能のバインド処理もすんでいるため、インスタンス生成時の他のクラスを利用する必要があるクラスをバインドする場合は、bootメソッドでバインドする

#### サービスコンテナのインスタンス取得方法
以下の3つの方法
```php
// app関数から取得
$app = app();

// Application::getInstanceメソッドから取得
$app = \Illuminate\Foundation\Application::getInstance();

// Appファザードから取得
$app = \App::getInstance();
```

#### バインドの種類
1. bind

以下のNumberクラスが定義されているとする
```php
class Number
{
    protected $number;

    public function __construct($number = 0)
    {
        $this->number = $number;
    }

    public function getNumber()
    {
        return $this->number;
    }
}
```

バインド
```php
app()->bind(Number::class, function() {
    return new Number();
})
```

解決
```php
$numcls = app()->make(Number::class)
$number = $numcls->getNumber();
```

**解決時にパラメータを指定する例**
バインド
```php
app()->bind(Number::class, function (Application $app, array $parameters) {
    return new Number($parameters[0]);
})
```

解決
```php
$numcls = app()->make(Number::class, [100]);
echo $numcls->getNumber();
```

**別名をバインドする例**
bindの第1引数に連想配列で指定

バインド
```php
// Numberクラスに対してnumという名前を割り当てる
app()->bind([Number::class => 'num']);
```

解決
```php
$numcls = app()->make('num');
$number = $numcls->getNumber();
```

2. binfif

引数で指定された文字列に対するバインドが存在しない場合のみバインド処理を行う  
(同名のバインドが既に存在する場合は何も行わない)

バインド
```php
app()->bindIf('Number100', function() {
    return new Number(100);
});

// 既に同名のバインドがあるため無効
app()->bindIf('Number100', function() {
    return new Number(200);
});
```

解決
```php
$numcls = app()->make('Number100');
$number = $numcls->getNumber(); // 100
```

3. singleton

インスタンスを一つのみにする場合に用いる  
サービスコンテナが解決したインスタンスはキャッシュされ、次からはキャッシュされたインスタンスが返される

以下のクラスがあるとする
```php
class RandomNumber extends Number
{
    public function __construct()
    {
        // Numberクラスのコンストラクタにランダムな値を渡す
        parent::__construct(mt_rand(1, 10000));
    }
}
```

バインド
```php
app()->singleton('random', function() {
    return new RandomNumber();
})
```

解決
```php
$number1 = app('random');
$number2 = app('random');
// $number1->getNumber() == $number2->getNumber() になる
```

4. instance

既に生成したインスタンスをサービスコンテナにバインドする

バインド
```php
$numcls = new Number(1001);
app()->instance('SharedNumber', $numcls);
```

解決
```php
$number1 = app('SharedNumber');
$number2 = app('SharedNumber');
// $number1->getNumber() === $number2->getNumber()
```

#### 別の文字列による解決処理のバインド
バインドメソッドの第2引数を文字列にすると、第1引数で指定した文字列を解決するときに、打2引数で指定した文字列を解決して結果を返すことが可能

バインド
```php
$app->singleton(Illuminate\Contracts\Http\Kernel::class,
                App\Http\Kernel::class);
```

解決
```php
// App\Http\Kernelというクラス名により解決される
$app = app(Illuminate\Contracts\Http\Kernel::class);
```

#### 複雑なインスタンス構築処理のバインド

以下のクラスがあるとする
```php
use Monolog\Logger;
use Psr\Log\LoggerInterface;
use Illuminate\Foundation\Application;

class Complex
{
    protected $logger;

    publc function __construct(LoggerInterface $logger)
    {
        $this->logger = $logger;
    }

    // 初期化処理を行う
    public function setup()
    {
        // do preprosessing
    }
}
```

はじめにComplexクラスが依存するPsr\Log\LoggerInterfaceインタフェースを実装したMonolog\Loggerのインスタンスを取得し、Complexクラスのコンストラクタに渡してインスタンスを生成

バインド
```php
app()->bind(Complex::class, function (Aplication $app) {
    $logger = $app->make(Logger::class);
    $complex = new Complex($logger);
    $complex->setup();

    return $complex;
})
```

#### 解決の種類
以下のバインドが定義されていたとする
バインド
```php
app()->bind(Number::class, function) {
    return New Number();
}
```

1. make

解決
```php
$number1 = app()->make(Number::class);
```

1. appヘルパ関数

解決
```php
$number2 = app(Number::class);
```

#### バインドしていない文字列の解決
バインドしていない文字列を解決する機能もある。解決する文字列がクラス名であり、かつ具象クラスであれば、バインドされた処理がなくても、サービスコンテナがそのクラスのコンストラクタを実行してインスタンスを生成する

以下のクラスがあったとする
```php
class Unbinding
{
    protected $name;

    public function __construct($name = '')
    {
        $this->name = $name;
    }
}
```

解決
```php
$unbinding1 = app()->make(Unbinding::class);
$unbinding2 = app()->make(Unbinding::class, ['Hoge']);
```

#### コンストラクタインジェクション
クラスのコンストラクタの引数でインスタンスを注入する方法　　

コンストラクタインジェクションでは、機能を渡されるクラスのコンストラクタ仮引数で必要なクラスをタイプヒンティングで指定する

以下のクラスがあったとする
```php
class UserService
{
    protected $sender;

    public function __construct(NotifierInterface $notifier)
    {
        $this->notifier = $notifier;
    }

    public function notice($to, $message)
    {
        $this->notifier->send($to, $message);
    }
}
```

解決
```php
$user = app()->make(UserService::class);
$user->send('to', 'message');
```

この例では、解決を依頼されたクラス名のコンストラクタ仮引数定義を読み取り、タイプヒンティングがクラス名やインタフェース名であればその解決を行い、取得したインスタンスをコンストラクタメソッドの引数に渡す（インタフェース名や抽象クラス名の場合は、解決方法がバインドされている必要がある）

#### メソッドインジェクション
メソッドの引数で必要とするインスタンスを渡す方法   
これを行うには、コンストラクタインジェクションと同様にメソッドの仮引数を使って、タイプヒンティングで必要とするクラスを指定する（インタフェース名や抽象クラス名の場合は、解決方法がバインドされている必要があるのも同様）

サービスコンテナのcallメソッドで対象メソッドを実行すると、リフレクションでタイプヒンティングのクラス名を取得し、サービスコンテナで解決し取得したインスタンスをメソッドに渡す流れになる

以下のクラスがあったとする
```php
class UserService
{
    public function notice(NotifierInterface $notifier, $to, $message)
    {
        $notifier->send($to, $message);
    }
}
```

解決
```php
$service = app(UserService::class);
app()->call([$service, 'notice'], ['to', 'message']);
```
callメソッドでは、第1引数に実行するクラス変数とメソッド名を指定  
第2引数にメソッドインジェクションで注入する値以外の引数を配列で指定

#### コンテキストに応じた解決
タイプヒンティングでインタフェース名を指定し、呼び出すクラス名で異なる具象クラスのインスタンスを取得することも可能

以下の二つのクラスがあったとする
```php
class UserService
{
    protected $notifier;

    public function __construct(NotifierInterface $notifier)
    {
        $this->notifier = $notifier;
    }
}

class AdminService
{
    protected $notifier;

    public function __construct(NotifierInterface $notifier)
    {
        $this->notifier = $notifier;
    }
}
```

バインド
```php
app()->when(UserService::class)  // 注入先のクラス名
     ->needs(NotifierInterface::class)  // 対象のタイプヒンティング
     ->give(PushSender::class)  // サービスコンテナで解決する文字列

app()->when(AdminService::class)
    ->needs(NotifierInterface::class)
    ->give(MailSender::class)
```