## 認証

`php artisan make:auth`でAuthに関するテンプレートとコントローラが作成される  
認証に使用するテーブルは初期からあるUserテーブル

コントローラ側
```php
$user = Auth::user();
// $userをビューに渡す
```

ビュー側
```php
@if (Auth::check()) // ログインしているか否か
<p>USER: {{$user->name . '(' . $user->email . ')'}}</p>
@else
<p>※ログインしていません(<a href="/login">ログイン</a>|<a href="/register">登録</a>)</p>
@endif
```

ログイン必須のページにする場合
```php
Route::get('hello', 'HelloController@index')->middleware('auth')
```

独自のログインページを作成して使用する場合
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