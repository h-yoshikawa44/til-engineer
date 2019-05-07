### Webページ表示の流れ
クライアント（URLにアクセス）
↓　↑
ルーティング（routes/web.php）
```php
Route::get('/', function () {
    return view('welcome');
});
```
↓　↑
resources/views/各ビュー  
例　welcome.blade.php

または

クライアント（フォームなども）
↓　↑
ルーティング（routes/web.php）
```php
Route::get('hello', 'HelloController@index');
```
↓　↑
コントローラ　⇔　モデル
（app/Http/Controllers/各コントローラ）
```php
return view('hello.index', $data);
```
↓　↑
ビュー（resources/views/各ビュー）

### artisan（アーティザン）コマンド
- 各ファイル作成  
  `php artisan make:(ファイル種類)　ファイル名`  
  例 `php artisan make:controller HelloController`

- migrate実行  
  `php artisan migrate`