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

### ミドルウェア
コントローラーの前処理、後処理を行う箇所。  

一つのコントローラーのアクションに割り当てるにはルーティングに記述するが、複数に割り当てるような場合は、グローバルミドルウェアを用いる。
Http/Kernel.phpにミドルウェアの登録を行っている箇所があるので、そこに追記する

また、ミドルウェアをグループ化して登録し、それを利用するようにすることも可能

### ヘルパ
#### redirect
引数ありの場合、RedirectResponseを返すもの
```php
       if ($validator->fails()) {
            // withErrors エラー情報リダイレクト先まで引き継ぐ
            // withInput　入力された情報リダイレクト先まで引き継ぐ
            return redirect('/validate')
                            ->withErrors($validator)
                            ->withInput();
        }
```
- withInput()　入力データを付加する
- withErrors(MessageProvider)　バリデータのエラーを付加する
- withCookie(クッキー配列）　クッキーを付加する

引数なしの場合はIlluminate\routingのRedirectorインスタンスを返す
- route（ルート名, 配列）　ルートの設定情報を指定（web.phpに記述したもの）
- action（アクションの指定, 配列）　アクション指定（HelloController@indexのように）
- view(ビュー名)　ビューを指定してリダイレクトする
- json(テキスト)　JSONデータを返す
- download(ファイルパス)　ファイルをダウンロード
- file(ファイルパス)


### バリデーション
送信されたデータをチェックする  
バリデーションによるエラーは$errorsに格納され、ビュー側に渡される  
エラーがある場合は、自動的に元のページにリダイレクトする(入力されていた値を設定しておきたい場合は、value="{{old('name')}}"のようにoldを使ってセットする

コントローラで手動で行う例
（※フォームリクエストもしくはバリデータで行うのが推奨）
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

#### フォームリクエスト
コントローラに渡されるリクエスト情報でバリデーションチェックを行うことができる。

フォームリクエストで、バリデーションのエラーメッセージを日本語にしたい場合はmessageメソッドをオーバーライドする
```php
    public function messages()
    {
        return [
            'name.required' => '名前は必ず入力してください',
            'mail.email' => 'メールアドレスが必要です',
            'age.numeric' => '年齢を整数で記入ください',
            'age.between' => '年齢は0~150の間で入力ください',
        ];
    }
```
#### バリデータ
独自ルールなど独自作成のバリデーションを利用する際に用いる。
$request->query()とすることで、クエリ文字列のチェックをすることも可能。
```php
   public function post(Request $request)
    {
        $rules = [
            'name' => 'required',
            'mail' => 'email',
            'age' => 'numeric|between:0,150'
        ];
        $messages = [
            'name.required' => '名前は必ず入力してください',
            'mail.email' => 'メールアドレスが必要です',
            'age.numeric' => '年齢を整数で記入ください',
            'age.between' => '年齢は0～150の間で入力してください'
        ];
        // バリデータの利用
        $validator = Validator::make($request->all(), $rules, $messages);
        // バリデーションチェックでエラーが出た場合の処理
        if ($validator->fails()) {
            // withErrors エラー情報リダイレクト先まで引き継ぐ
            // withInput　入力された情報リダイレクト先まで引き継ぐ
            return redirect('/validate')
                            ->withErrors($validator)
                            ->withInput();
        }
        return view('validate.index', ['msg' => '正しく入力されました']);
    }
```

独自ルールの例
```php
 $validator->sometimes('age', 'min:0', function($input) {
            return !is_int($input->age);
        });
```

#### 独自バリデータ
Validatorクラスを継承したクラスを作成  
validate〇〇のメソッドで、〇〇の部分がルールの名前になる
```php
// 独自バリデータクラス
class HelloValidator extends Validator
{
    public function validateHello($attribute, $value, $parameters)
    {
        return $value % 2 == 0;
    }
}
```

サービスプロバイダで独自バリデータクラスを設定
（このサービスプロバイダもapp.phpに登録しておく）
```php
    public function boot()
    {
        $validator = $this->app['validator'];
        $validator->resolver(function($translator, $data, $rules, $messages) {
            return new HelloValidator($translator, $data, $rules, $messages);
        });
    }
```

これでこのルールが使用できるようになる

※サービスプロバイダにルールを書く方法もある  
こちらは一つのコントローラーでのみ使うルールなどに有効であるが、汎用性はない
```php
        Validator::extend('hello', function($attribute, $value, $parameter, $validator) {
            return $value % 2 == 0;
        });
```


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

### データベース
接続設定はconfig/database.php

#### DBクラスでのCRUD
Illuminate\Support\Facades\DB;

データ取得の例
```php
$items = DB::select('select * from people');

$items = DB::select('select * from people where id = :id', $param);
```

データ挿入の例
```php
    $param = [
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];
        DB::insert('insert into people (name, mail, age) values (:name, :mail, :age)', $param);
```

データ更新の例
更新画面に行く際にSELECTでデータをあらかじめ取得しビューに渡す（$formで渡したとする） 
$form->カラム名　で既存データを入力フォームにはめ込んでいく
IDはフォームにhiddenでいれる
```html
<input type="hidden" name="id" value="{{$form->id}}">
```
```php
    $param = [
            'id' => $request->id,
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];
        DB::update('update people set name = :name, mail = :mail, age = :age where id = :id', $param);
```

データ削除の例
```php
    $param = ['id' => $request->id];
    DB::delete('delete from people where id = :id', $param);
```

#### クエリビルダ（DBクラス）
データ取得の例
get()に引数を渡して、カラム名の指定も可能
```php
$items = DB::table('people')->get();
```
- where(フィールド名, 値)　もしくは　where(フィールド名, 演算記号, 値)  
  WHERE句。where句を複数使用するとAND条件になる。またlike条件も指定可能
- orwhere(フィールド名, 値)　OR条件
- whereRaw(条件式, パラメータ配列)　条件式をそのまま書ける方式　
```php
    $min = $request->min;
    $max = $request->max;
    $items = DB::table('people')
        ->whereRaw('age >= ? and age <= ?', [$min, $max])->get();
```
- orderBy(フィールド名, ascまたはdesc)　並び順指定
- offset(整数)　指定した位置からレコードを取得
- limit(整数)　指定した数だけレコードを取得
- first()　最初のレコードを返す

データ挿入の例
```php
    $param = [
        'name' => $request->name,
        'mail' => $request->mail,
        'age' => $request->age,
    ];
    DB::table('people')->insert($param);
```

データ更新の例
whereを挟まずにupdateした場合は、全レコードが更新対象になる
```php
    $param = [
        'id' => $request->id,
        'name' => $request->name,
        'mail' => $request->mail,
        'age' => $request->age,
    ];
    DB::table('people')
        ->where('id', $request->id)
        ->update($param);
```

データ削除の例
```php
    DB::table('people')
        where('id', $request->id)->delete();
```

#### マイグレーションファイル
upメソッドにテーブル生成処理を書く

Schema::create　テーブル作成

フィールド
- $table->increment(フィールド名)　プライマリキー
- $table->integer(フィールド名)
- $table->BigInteger(フィールド名)
- $table->float(フィールド名)
- $table->double(フィールド名)
- $table->char(フィールド名)
- $table->string(フィールド名)
- $table->text(フィールド名)
- $table->longText(フィールド名)
- $table->boolean(フィールド名)
- $table->date(フィールド名)
- $table->dateTime(フィールド名)
- $table->timestamp()　タイムスタンプ（created_atとmodified_at）

Schema::drop　テーブル削除
$schema::dropIfExists(テーブル名)　テーブルがあれば削除

#### シーダー
データベースのデータを挿入するためのファイル

 `php artisan db:seed`で実行されるのは、DatabaseSeederであるため、他のSeederを作成した場合は、DatabaseSeederに登録しておく必要がある

 ```php
    $this->call(PeopleTableSeeder::class);
```


### artisan（アーティザン）コマンド
- 各ファイル作成  
  `php artisan make:(ファイル種類)　ファイル名`  
  例 `php artisan make:controller HelloController`

  - contoroller(app/Http/Controllers)
  - provider(app/Providers)
  - middleware(app/Http/Middleware)
  - request(app/Http/Requests)
  - migration(database/migrations)
  - seeder(database/seeds)

- migrate実行  
  `php artisan migrate`

- seederを実行  
  `php artisan db:seed`