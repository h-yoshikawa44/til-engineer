## OAuth認証
TwitterやGoogleなどの外部サービスのアカウントをログイン認証に利用するケース。  
外部サービスに認証を委託し、アプリケーション側ではユーザ情報だけを管理する仕組み。

### Socialite
このパッケージでOAuth2.0またはOAuth1.0に対応してクライアントを利用できる
Twitter、Facebook、Linkedln、Google、GitHub、BitBubketの認証ドライバが用意されている  
また、提供されていないサービスであっても、独自のOAuth認証どらーばを用意することで利用可能

・インストール
  `composer require laravel/socialite`

・外部サービスでアプリケーションの登録
GitHubの場合、GitHubにログイン後　[settings] → [Developer setting]に遷移して[OAuth Apps]の[New OAuth App]で登録する
[Homepage URL]にWebアプリケーションのURL、OAuth認証で利用する[Authorization callback URL]にGitHub認証後にリダイレクトされるアプリケーションのURLを設定

・config/services.phpに以下の設定値を記述
　（GitHubに登録時に発行されるclient_idとclient_secretの値を設定する）
```php
'github' => [
    'client_id' => env('GITHUB_CLIENT_ID'),
    'client_secret' => env('GITHUB_CLIENT_SECRET'),
    'redirect' => 'http://localhost/register/callback' // 外部サービスとの認証処理で利用するアプリケーションのコールバックURL
],
```

・外部サービスへのリダイレクトの実装  
（App\Http\Controllers\Register\RegisterAction.phpを参照）

・外部サービスからリダイレクト後の実装  
標準のApp\Userクラスを利用する場合は、標準で用意されているusersテーブルのpasswordカラムが必須であるため、アプリケーションの仕様に合わせてnullableを設定するか、passwordカラムを削除するなどテーブル定義を変更する
(App\Http\Conrollers\Register\CallbackAction.phpを参照)


・アクセスのスコープ追加
外部サービスのアクセス権は、各サービスが提供するOAuthOAuthのドキュメントなどを参照して、必要に応じて指定する

追加例
```php
$driver = \Socialite::driver('github');
$driver->scope(['user:follow']);
```

設定しなおし例
```php
$driver = \Socialite::driver('github');
$driver->setScopes(['user:email', 'user:follow']);
```

・ユーザ情報アクセス時にパラメータ追加例(GETリクエストで送信されるパラメータ)
```php
$driver = \Socialite::driver('github');
$driver->with(['allow_signup' => 'false']);
```

・ステートレス
一部の外部サービスではアプリケーション側で状態を保持する必要がある。その場合はアプリケーションでstatelessメソッドを利用して、セッションに状態を保持する
```php
$driver = \Socialite::driver('facebook');
$driver->stateless()->user();
```

### OAuthドライバの追加
Socialite Providersに用意されていない外部サービスでも、アプリケーション独自の認証ドライバを簡単に実装できる

Amazonの例  
・Amazonにアプリケーションの登録  
[Login with Amazon]の[Developer Center]→[Getting Started]から[Web]を選択して、[Register Your Application]→[App Console]に遷移して登録  
client_idとclient_secretが発行され、発行後、AmazonからコールバックされるURLを[Allowed Return URLs]に記述

・config/service.phpに以下を追記
```php
'amazon' => [
    'client_id' => env('AMAZON_CLIENT_ID'),
    'client_secret' => env('AMAZON_CLIENT_SECRET'),
    'redirect' => 'https://localhost/register/callback'
]
```

AmazonのOAuth認証はOAuth2.0なので、SocialiteのLaravel\Socialite\Two\AbstractProviderクラスを継承して利用する
以下の抽象メソッドを実装する
- getAUthUrl　OAuth認証を提供しているサービスの認証を提供するURLを文字列で記述
- getTokenUrl　OAuth認証を提供しているサービスのトークンを取得するURLを文字列で記述
- getUserByToken　取得したトークンを利用して、ユーザ情報を取得するメソッド。取得したユーザ情報を配列で返却する
- mapUserToObject　getUserByTokenで取得した配列をLaravel\Socialite\Two\Userインスタンスに変換して返却する

・Amazon OAuth認証ドライバの実装
メソッドに対応するURLは[Developer Center]のドキュメントに公開されているので、それぞれに対応したURLを記述し実装する
（App\Foundation\Socialite\AmazonProviderを参照）

・サービスプロバイダに認証ドライバとして追加
（App\Providers\SocialiteServiceProvider.php参照）

・Amazonドライバの利用
```php
    public function __invoke(Factory $factory): RedirectResponse
    {
        return $factory->driver('amazon')->redirect();
        // もしくは
        // \Socialite::driver('amazon')->redirect();
    }
```