## ビュー

### リクエスト情報の取得
- 現在のURLを取得(GETパラメーター（ ? 以降の文字列）も取得)  
`{{ request()->fullUrl() }}`
- 現在のURLを取得（GETパラメーター（ ? 以降の文字列）は取得しない  
`{{ url()->current() }}`
- 現在のURLを相対で取得（GETパラメーター（ ? 以降の文字列）も取得  
`{{ str_replace(url('/'),"",request()->fullUrl()) }}`
- リクエストURIの取得  
`{{ request()->path() }}`
- リクエストのURIが指定されたパターンにマッチするか  
```php
@if ( request()->is('*users*') )
        マッチします<br>
@endif
```
- 完全なURLを取得(クエリ文字列なし)  
`{{ request()->url() }}`
- リクエストメソッドの取得  
`{{ request()->method() }}`
- リクエストメソッドが指定したものにマッチするか  
```php
@if( request()->isMethod('get') )
     getメソッドです<br>
@endif
```

### CSRFトークン
フォームでは必ずCSRFトークンを含める必要がある  
{{ csrf_field()}}をフォーム内にいれること

### 他ファイルのインポート
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
<li>{{$item['name']}}</li>　　@each('components.item', $data, 'item')
※$dataはコントローラから渡された配列の変数

### ページネーション
コントローラ  
orderByで順番の指定などもできる。その場合は、必ずsimplepaginateが最後に来るようにする
```php
    public function index(Request $request)
    {
        // 1ページ当たりの表示数を指定
        $items = DB::table('people')->simplePaginate(5);

        // モデル利用の場合
        //$items = Person::all()->simplepaginate(5);
        return view('pagenate.index', ['items' => $items])
    }
```

ビュー
※$itemsはDBからの取得データ  
以下でページ送りのリンク作成
```php
 {{$items->links()}}
 ```
 パラメータの追加
 ```php
 {{$items->appends(['sort' => $sort])->links()}}
 ```

ページ数指定のページ送りをする場合の例  
pagenateで指定する
```php
$items = Person::orderBy($sort, 'asc')->paginate(2);
```

#### リンクのテンプレート
リンクを生成するlinksメソッドは、使用するテンプレートを指定することもできる  
テンプレートを作成
` php artisan vendor:publish --tag=laravel-pagination`
