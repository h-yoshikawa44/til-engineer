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

一つのコントローラーのアクションに割り当てるにはルーティングに記述する
例
```php
Route::get('middleware', 'MiddlewareController@index')->middleware(HelloMiddleware::class);
```

複数のコントローラに割り当てるような場合は、グローバルミドルウェアを用いる。
app/Http/Kernel.phpにミドルウェアの登録を行っている箇所があるので、そこに追記する  
こちらの場合は全てのコントローラに適用される

また、ミドルウェアをグループ化して登録し、それを利用するようにすることも可能