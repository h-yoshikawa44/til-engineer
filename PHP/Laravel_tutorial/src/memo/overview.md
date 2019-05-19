## Webページ表示の流れ
クライアント（URLにアクセス）
↓　↑
ルーティング（routes/web.php）
```php
Route::get('/', function () {
    return view('welcome');
});
```
↓　↑
resources/views/各ビューフォルダ/各ビューファイル  
例　welcome.blade.php

または

クライアント（フォームなども）
↓　↑
ルーティング（routes/web.php）
```php
Route::get('hello', 'HelloController@index');
```
※リクエスト情報＋フォームリクエスト（バリデーション）
↓　↑
ミドルウェア（アクション前、アクション後）
（$next実行。複数ミドルウェアがある場合は次のミドルウェアに。
　ない場合は、コントローラーのアクションへ）
↓　↑
コントローラ　⇔　モデル
（app/Http/Controllers/各コントローラ）
```php
return view('hello.index', $data);
```
レンダリング（＋ビューコンポーザ）
↓　↑
ビュー（resources/views/各ビューフォルダ/各ビューファイル）


## RESTful
ルーティングにリソースで7つのアクションをまとめて登録

```php
Route::resource('rest', 'RestappController');
// 7つのアクションを一括登録
// rest(GET)　index
// rest/create(GET)　create
// rest(POST)　store
// rest/番号(GET)　show(番号=ID)
// rest/番号/edit(GET)　edit(番号=ID)
// rest/番号(PUT/PATCH)　update(番号=ID)
// rest/番号(DELEtE)　delete(番号=ID)
```
なお、これではRourcefulになってしまうが、このうちcreateとeditを除いた5つのメソッドRESTfulだといえる  
(RESTfulではCRUDはすべて同じアドレスで、アクセスに使うHTTPメソッドの違いによって処理を分ける)

Laravelでは配列をリターンすると自動的にJSONに変換してくれる
```php
    public function index()
    {
        $items = Restdata::all();
        // 配列にしてリターン（JSONにしてリターン）
        return $items->toArray();
    }
```
