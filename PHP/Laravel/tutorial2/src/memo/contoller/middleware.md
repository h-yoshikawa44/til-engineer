## ミドルウェア
パス(namespace)：App\Http\Middleware
基底クラス：なし

コントローラーの前処理、後処理を行う箇所  
用途としては、RequestやResponseに含まれる値の変更や暗号化（復号）やセッション実行、認証処理など  
複数のミドルウェアを連続して使用することもできる

handleメソッドが実行されるメソッド

例
```php
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $data = [
            ['name' => 'taro', 'mail' => 'tato@yamada'],
            ['name' => 'hanako', 'mail' => 'hanako@flower'],
            ['name' => 'sachiko', 'mail' => 'sachico@happy'],
        ];
        // フォーム送信などで送られる値に新たな値を追加するもの
        $request->merge(['data' => $data]);
        // コントローラーの前処理
        return $next($request);

        // 以下のようにするとコントローラーの後処理が書ける
        // $response = $next($request)
        // 処理
        // return $response
    }
}
```


### グローバルミドルウェア
複数のコントローラに割り当てるような場合は、グローバルミドルウェアを用いる。
app/Http/Kernel.phpにミドルウェアの登録を行っている箇所があるので、そこに追記する  
こちらの場合は全てのコントローラに適用される  
グローバルミドルウェアではルート情報を扱う処理はできない

また、ミドルウェアをグループ化して登録し、それを利用するようにすることも可能

- Illuminate\Foundation\Http\Middleware配下
  - CheckForMaintenanceMode  
    メンテナンス中の場合は全てのサクセスをメンテナンス画面を表示する
  - ValidatePostSize  
    リクエストボディのサイズをチェックし、この値が不正な場合はIllunate\Http\Exceptions\PostTooLangeExceptionをスローする
  - ConvertEmptyStringsToNull  
    リクエストの中でから文字列をNullに変換
- App\Http\Middleware配下
  - TrimStrings  
    リクエスト文字列に対してtrim処理を行い空文字を削除する。exceptプロパティにこの処理を除外したいリクエストパラメータを指定できる
  - TrustProxies  
    ロードバランサなどを使用している場合に、アプリケーション内でHTTPSのリンクなどが生成されない場合に、信頼できるアクセス元としてproxiesプロパティへロードバランサなどを配列で追加することで作用する

### ルートミドルウェア
デフォルトではwebミドルウェアグループに記述されている  
routes/web.phpでのルートはweb、routes/api.phpでのルートはapiを使用するようになっている（変更したい場合は、RouteServiceProviderを修正する）

- App\Http\Middleware配下
  - EncryptCookies  
    クッキーの暗号化及び復号化を行う。ほかのアプリケーションで発行されたクッキーは復号出来ないため、復号対象から除外したいクッキーをexceptプロパティの配列で指定
  - VerifyCsrfToken  
    CSRF対策のXSRF-TOKEN発行やトークンのチェックを行う。HEADリクエスト、GETリクエスト、OPTIONSリクエスト以外を対象に動作する。対象から除外したいURIをexceptプロパティで指定
- Illuminate\Cookie\MiddlewareAddQueuedCookiesToResponse  
  Cookie::queueで登録した値をレスポンスにクッキーとして追加して返却する
- Illuminate\Session\Middleware
  - StartSesion  
    セッションを有効にし、レスポンス返却時にセッションに書き込む
  - AuthenicateSession  
    パスワード変更時に他のデバイスでログインしている、対象ユーザをログアウトさせる（デフォルトではコメントアウトされている）
- Illuminate\View\Middleware\ShareErrorsFromSession  
  Bladeテンプレートのerrors変数に、セッションのerrorsキーから取得したエラー内容を埋め込む
- Illuminate\Routing\Middleware\SubstituteBindings  
  Eloquentモデルと結合させて、ルートに利用されるid等からデータベース検索を行い、コントローラクラスやルートで利用可能にする。この機能はRoute Model Bindingと呼ばれる


### 名前付きミドルウェア
ルーターへの登録、またはコントローラクラスのコンストラクタなどで任意の名前を指定して利用する

一つのコントローラーのアクションに割り当てるにはルーティングに記述する
例
```php
Route::get('middleware', 'MiddlewareController@index')->middleware(HelloMiddleware::class);
```

- auth(Illuminate\Auth\Middleware\Authenticate)  
  認証済みユーザかどうかを判定する。認証済みユーザではない場合はIllminate\Auth\AuthenticationExceptionがスローされる
- auth.basic(Illuminate\Auth\Middleware\AuthenticateWithBasicAuth)  
  Basic認証を行う
- bindings(Illuminate\Routing\Middleware\SubstrituteBindings)  
  グローバルミドルウェアで記述されているものと同一
- cache.headers(Illuminate\Http\Middleware\SetCacheHeaders)  
  ETag(エンティティタグ)を利用し、コンテンツのキャッシュを制御する。このミドルウェアは5.6以降で用意されている
- can(Illuminate\Auth\Middleware\Authorize)  
  特定モデルやリソースへのアクションに対して認可を行う。この機能は認可機能と組み合わせて利用する
- thtottle(Illuminate\Routing\Middleware\ThrottleRequests)  
  同一ユーザが単位時間内に規定回数以上のアクセスを行ったかどうかを判定する。規定回数以上アクセスした場合はIlluminate\Http\Exceptions\ThrottleRequestsExceptionがスローされる
- signed(Illuminate\Routing\Middleware\ValidateSignature)  
  署名付きアクセスで有効な署名かどうか、制限時間内のアクセスかどうかを判定する。このミドルウェアは5.6以降で用意されている
- guest(App\Http\Middleware\RedirectifAuthenticated)  
  認証済みかどうかを判定し、認証済みの場合は/homeにリダイレクトする。リダイレクト先が固定されているので、アプリケーションに合わせたミドルウェアを用意して利用する