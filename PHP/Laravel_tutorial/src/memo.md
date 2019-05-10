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

### ミドルウェア
コントローラーの前処理、後処理を行う箇所。  

一つのコントローラーのアクションに割り当てるにはルーティングに記述するが、複数に割り当てるような場合は、グローバルミドルウェアを用いる。
Http/Kernel.phpにミドルウェアの登録を行っている箇所があるので、そこに追記する

また、ミドルウェアをグループ化して登録し、それを利用するようにすることも可能

### バリデーション
送信されたデータをチェックする  
バリデーションによるエラーは$errorsに格納され、ビュー側に渡される
```php
    public function post(Request $request)
    {
        // バリデーションルール
        $validate_rule = [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|between:0,150',
        ];
        // バリデーション実行
        // 問題がなければ続きの処理へ
        // 問題があれば例外が発生し、その場でフォームページを表示するレスポンスが生成され返される
        $this->validate($request, $validate_rule);
        return view('validate.index', ['msg' => '正しく入力されました']);
    }
```


バリデーションルール
- accept　true、on、yes、1といった値かどうか
- active_url, url　指定されたアドレスが実際に有効であるか
- after:日付　指定した日付より後であるか
- after_or_equal　指定した日かそれより後であるか
- before:日付　指定した日付より前であるか
- before_or_equal　指定した日かそれより前であるか
- alpha　すべてアルファベットであるか
- alpha-dash　アルファベット＋ハイフン＋アンダースコアであるか
- alpha-num　アルファベットと数字であるか
- array　配列であるか
- between:最小値, 最大値　値が指定の範囲内であるか
- boolean　真偽値かどうか
- date　日時の値として扱える値かどうか（strtotimeで変換できるか）
- date_format:フォーマット　値が指定フォーマットの定義に一致するか
- different:フィールド　指定フィールドと違う値かどうか
- same:フィールド　指定フィールドと同じ値かどうか
- digits:桁数　指定された桁数かどうか
- digits_between:最小桁数, 最大桁数　桁数が指定範囲内かどうか
- distinct　配列内に同じ値がないかどうか
- email　メールアドレス形式かどうか
- exists:テーブル, カラム　値が指定データベースの指定カラムにあるかどうか　
- filled　空でないか
- required　必須項目
- in:値1, 値2...　指定した値の中に含まれるかどうか
- not_in:値1, 値2...　指定した値の中に含まれないかどうか
- integer　整数であるか
- numeric　数値であるか
- ip　IPアドレスかどうか
- ipv4　IPv4であるかどうか
- ipv6　IPv6であるかどうか
- json　JSON形式であるかどうか
- min:値　指定値よりも小さいか
- max:値　指定値よりも大きいか
- regix:パターン　正規表現にマッチするかどうか
- size:値　文字列ならば文字数、数値の場合は整数値、配列の場合は要素数が一致するか
- string　文字列かどうか
- unique:テーブル, カラム　指定テーブルの指定カラムに同じ値が存在しないか


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
  - middleware(app/Http/Middleware)

- migrate実行  
  `php artisan migrate`