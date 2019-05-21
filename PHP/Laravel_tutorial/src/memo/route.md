## ルーティング
パス：routes/web.php(APIの場合はapi.php)
)

定義されたルートの中からRequestにマッチするルートを探して、定義されているコントローラやアクションクラス、クロージャといったアプリケーションコードを実行

実行結果として、コントローラやミドルウェアからレスポンスとして返される値を返すことで、これをIlluminate\Http\ResponseもしくはIlluminate\Http\JsonResponseのインスタンスに変換して返す

### ビューを返す
```php
Route::get('/', function () {
    return view('welcome');
});
```

### コントローラを呼び出す
URLにパラメータを入れる場合は{}で囲う。  
?をつけることでnull許容になる（コントローラ側でデフォルト値を設定しておくこと）
```php
Route::get('hello/sample/{id?}/{pass?}', 'HelloController@sample');

Route::post('validate', 'ValidateController@post');
```

シングルアクションコントローラを呼び出す場合は、アクションメソッド名は指定せず、コントローラ名のみ指定する

### resource
```php
Route::resource('rest', 'RestappController');
```
7つのアクションを一括登録
- rest(GET)　index
- rest/create(GET)　create
- rest(POST)　store
- rest/番号(GET)　show(番号=ID)
- rest/番号/edit(GET)　edit(番号=ID)
- rest/番号(PUT/PATCH)　update(番号=ID)
- rest/番号(DELEtE)　delete(番号=ID)
```