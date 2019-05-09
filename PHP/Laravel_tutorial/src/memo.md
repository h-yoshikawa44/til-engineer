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
resources/views/各ビューフォルダ/各ビューファイル  
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
レンダリング（＋ビューコンポーザ）
↓　↑
ビュー（resources/views/各ビューフォルダ/各ビューファイル）

### ビューファイル
#### レイアウト
resources/views/layoutsにレイアウトの土台となるファイルを作成  
そのレイアウトを継承したビューファイルを作成し、@yeildにはめ込む@sectionを書いていく

レイアウト　　　　　　　継承ファイル
(helloapp.blade.php)　(index6.blade.php)
　　　　　　　　　　　　　　@extends('layouts.helloapp')
@yeild('title')　　　←　　@section('title', 'Index')

　　　　　　　　　　　　　　@parent
@section('menubar')　←　　@section('menubar')

#### コンポーネント
resources/views/componentsにビューの部品となるコンポーネントを書いていくファイルを作成  
ヘッダーやフッターなどはコンポーネントで作成するとよい  
コンポーネントをはめ込むところで@componentで指定  
コンポーネント側で使用する変数の値は@slotで設定する

コンポーネント　　　　　ビューファイル
(message.blade.php)　　(index6.blade.php)
※コンテンツを定義　　→　　@component('components.message)
{{$msg_title}}　　　→　　　　　@slot('msg_title')
　　　　　　　　　　　　　　　　　　CAUTION!
　　　　　　　　　　　　　　　　@endslot
　　　　　　　　　　　　　 @endcomponent


#### サブビュー
あるビューから別のビューを読み込んではめこんだもの  
@slotは使えないが、コントローラから渡された変数は使用可能

ビューファイル　　　　　ビューファイル
(message.blade.php)　　(index6.blade.php)
※コンテンツを定義　　→　@include('components.message', 
{{$msg_title}}　　　　　　[('msg_title' => 'OK',
{{$msg_content}}　　　　　'msg_content' => 'サブビューです')])


#### コレクションビュー
配列やコレクションを順に取り出し、テンプレートにはめ込んで出力するもの  

コンポーネント　　　　　　　　　ビューファイル
(item.blade.php)　　　　　　　(index6.blade.php)
<li>{{$item['name']}}</li>　　@each('components.item', $data, 'item)
※$dataはコントローラから渡された配列の変数

#### ビューコンポーザ
このビューでは常にこの処理をしてこの結果をビューに渡すといった際に使用する。  
サービスプロバイダに呼び出し処理を書いておく（config/app.phpにプロバイダを登録）ことで、指定したビューが呼び出されたときに割り込んで処理される

HelloServiceProvider.php
bootメソッド
```php
    public function boot()
    {
        // ビューコンポーザ
        View::composer(
            'hello.index6', 'App\Http\Composers\HelloComposer'
        );
    }
```
↓
HelloComposer.php
```php
    public function compose(View $view)
    {
        // ビューに値をセット
        $view->with('view_message',
                    'this view is "' . $view->getName() . '"!!');
    }
```

### artisan（アーティザン）コマンド
- 各ファイル作成  
  `php artisan make:(ファイル種類)　ファイル名`  
  例 `php artisan make:controller HelloController`

  - contoroller(app/Http/Controllers)
  - provider(app/Providers)

- migrate実行  
  `php artisan migrate`