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
- /auth/register　ユーザ登録及びメール送信
- /auth/login　ログイン
- /auth/logout　ログアウト

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

### ログインした状態での処理分け
コントローラ側  
ユーザ情報を取得してビューに渡す
```php
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