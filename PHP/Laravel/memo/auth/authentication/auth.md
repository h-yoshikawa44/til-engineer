## 認証

`php artisan make:auth`でAuthに関するテンプレートとコントローラが作成される  
認証に使用するテーブルは初期からあるUserテーブル

作成されるもの
app/Http/Controllers
- HomeController

resources/views
- auth/passwords/email.blade.php
- auth/passwords/reset.blade.php
- auth/register.blade.php
- auth/verify.blade.php
- home.blade.php
- layouts/app.blade.php

初期からあるが認証で使用するもの
app/Http/Controllers
- Auth/ForgotPasswordController.php
- Auth/LoginController.php
- Auth/RegisterController.php
- Auth/ResetPasswordController.php
- Auth/VerificationController.php

database/factories
- UserFactory.php

database/migrations
- 2014_10_12_000000_create_table_user_table.php
- 2014_10_12_100000_create_password_resets_table.php

resource/view
- login.blade.php

### 構成
#### ルート
```php
Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');
```

- /home　トップ画面表示

Auth::routes()で定義されるルート
|メソッド|パス|名前|アクション|ミドルウェア|
|---|---|---|---|---|
|GET|login|login|App\Http\Controllers\Auth\LoginController@showLoginForm|web, guest|
|POST|login|---|App\Http\Controllers\Auth\LoginController@login|web, guest|
|POST|logout|logout|App\Http\Controllers\Auth\LoginController@logout|web|
|POST|password/email|password.email|App\Http\Controllers\Auth\ForgotPasswordController@sendResetLinkEmail|web, guest|
|GET|password/reset|password.request|App\Http\Controllers\Auth\ForgotPasswordController@showLinkRequestForm|web, guest|
|POST|password/reset|---|App\Http\Controllers\Auth\ResetPasswordController@reset|wec, guest|
|GET|password/reset/{token}|password.reset|App\Http\Controllers\Auth\ResetPasswordController@showResetForm|web, guest|
|GET|register|register|App\Http\Controllers\Auth\RegisterController@showRegistrationForm|web, guest|
|POST|register|---|App\Http\Controllers\Auth\RegisterController@register|web, guest|

RegisterControllerのredirectToプロパティでユーザ登録後のリダイレクト先を指定できる  
単純なリダイレクトでないときはregisteredメソッドをオーバーライドして利用する

```php
protected function registered(Request $request, $user): Redirector
{
    if ($user instanceof User){
        logger()->info('user login');
    }
    return redirect(route('index'));
}
```

#### コントローラ
・RegisterController  
ユーザ登録画面をつかさどる

useしている`RegistersUsers`トレイトに登録処理の実装がある
`showREgistrationForm`メソッドで`auth.register`のビューを返す

`register`メソッドで入力送信されたデータをバリデータでチェックし、登録後、ログインしてリダイレクトしている


・loginController
ログイン画面をつかさどる

useしている`AuthenticatesUsers`トレイトにログイン画面の実装がある
`showLoginForm`メソッドで`auth.login`のビューを返す

`login`メソッドで入力された値をチェックして、ログイン処理を行い、リダイレクトしている

`logout`メソッドでログアウト（※ログアウト後のリダイレクトパスが`/`になっているので、変更する場合はオーバーライドする）

### メソッド
#### attempt
ユーザ情報を利用してログインを行う

メソッドの流れ
1. ログインが実行されたことをイベントとして発行。ログイン時に利用する資格情報と自動ログインであるかをイベントに送信
2. Illuminate\Contracts\Auth\UserProviderインタフェースを実装したクラスに資格情報が正しいか問い合わせる
3. ユーザ情報の取得後、資格情報から取得したデータがユーザ情報と同じものかを調べる。標準で用意されている処理では、フォームなどの入力パスワードとデータベースの暗号化されたパスワードのハッシュが同一であるか確認する。正しければログイン処理を実行する
4. ログインできなかった場合は、その情報をイベントとして発行する

#### user
認証ユーザ情報にアクセスする場合に利用する

1. ログアウト済みの場合は認証ユーザ情報を返却せず、処理を停止
2. ログイン済みでインスタンス内にユーザ情報が保持されている場合は、以降の処理は行わずに認証ユーザ情報を返却する
3. セッションからユーザ情報にアクセスするための識別可能な値（IDなど）を取得
4. セッションから取得した値を利用して、データベースへの問い合わせて結果を取得
5. データベースからユーザ情報を取得できた場合は、認証処理の実行を通知するイベントを発行
6. cookieからログイン情報に関連する値を取得し、トークンの値とIDを用いてデータベースに問い合わせ、自動ログイン対象かどうかを調べる
7. 認証ユーザの特定可能なIDをセッションに書き込み、セッションのアップデートを実行する

### ログインした状態での処理分け
コントローラ側  
ユーザ情報を取得してビューに渡す
```php
use Illuminate\Support\Facades\Auth;

$user = Auth::user();
// $userをビューに渡す
```

ビュー側
Auth::checkでログインしているかどうかを確認
```php
@if (Auth::check()) // ログインしているか否か
<p>USER: {{$user->name . '(' . $user->email . ')'}}</p>
@else
<p>※ログインしていません(<a href="/login">ログイン</a>|<a href="/register">登録</a>)</p>
@endif
```

#### ログイン必須のページにする場合
```php
Route::get('hello', 'HelloController@index')->middleware('auth')
```

複数のルートをまとめて定義する場合
```php
Route::group(['middleware' => 'auth', function(){
    // 各ルートを定義
});
```

#### 独自のログインページを作成して使用する場合
```php
    public function postAuth(Request $request)
    {
        $email = $request->email;
        $password = $request->password;
        if(Auth::attempt(['email' => $email, 'password' => $password])){
            $msg = 'ログインしました(' . Auth::user()->name . ')';
        } else {
            $msg = 'ログインに失敗しました';
        }
        return view('paginate.auth', ['message' => $msg]);
    }
```

### 認証処理の内容
Laravelではデータベースを用いて認証処理を実行するIlluminate\Auth\EloquentUserProviderクラスとIlluminate\Auth\DatabaseUserProderクラスが標準で用意されている  

この二つのクラスが実装しているのはIIluminate\Contracts\Auth\UserProviderインタフェースが提供している機能
- retrieveById  
  ログイン後にセッションに保持されるユーザIDなどユニークな値を利用し、ユーザ情報の取得を行う
- retrieveByToken  
  クッキーから取得したトークンを利用して、ユーザ情報の取得を行う（クッキーによる自動ログイン利用時など）
- updateRememberToken  
  retrieveByTokenメソッドで利用するトークンの更新を行う
- retrieveByCredentials  
  資格情報を用いてログインを行うattemptメソッドが内部でコールするメソッドで、指定された配列の情報を利用してユーザ情報の取得を行う
- validateCredentials  
  attempsメソッドで指定された認証情報の検証を行う（パスワードのハッシュ値の比較など）

これらのメソッドの引数または戻り値にIlluminate\Contracts\Auth\Authenticatableインタフェースを実装したクラスを取る  
このインタフェースのメソッド
- getAuthIdentifierName  
  ユーザを特定するために利用する識別子の名前を返却する。一般的にはデータベースのカラム名などが該当
- getAuthIdentifier  
  ユーザ特定が可能な値を返却する。getAuthIdentifierNameメソッドで指定した値を使って配列などから取り出す
- getAuthPassword  
  ユーザのパスワードを返却する
- getRememberToken  
  自動ログインなどに利用するトークンの値を返却する
- setRememberToken  
  自動ログインなどに利用するトークンの値をセットする
- getRememberTokenName  
  自動ログインなどに利用するトークンの名前を返却する

独自認証処理の実装はこの2つのインタフェースを組み合わせて対応できる

Authenticatableインタフェースの実装例(自動ログインを利用しない例)
```php
namespace App\Auth;

use Illuminate\Contracts\Auth\Authenticatable;

class AuthenticateUser implements Authenticatable
{
    protected $attributess;

    public function __construct(array $attributes)
    {
        $this->attributes = $attributes;
    }

    public function getAuthIdentifierName(): string
    {
        return 'user_id';
    }

    public function getAuthIdentifier()
    {
        return $this->attributes[$this->getAuthIdentifierName()];
    }

    public function getAuthPassword(): string
    {
        return $this->attribute['password'];
    }

    public function getRememberToken(): string
    {
        $this->attributes[$this->getRememberTokenName()];
    }

    public function setRememberToken(string $value)
    {
        $this->attributes[$this->getRememberTokenName()] = $value;
    }

    public function getRememberTokenName(): string
    {
        return '';
    }
}
```
