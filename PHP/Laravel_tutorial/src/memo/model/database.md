## データベース
接続設定はconfig/database.php

### DBクラスでのCRUD
DBクラスパス：Illuminate\Support\Facades\DB
以下のDBクラスを用いた処理を書く際はuseしておく

DBクラスを用いて、DBにアクセスしてCRUD操作できる。モデルクラスを用いてもアクセスしてCRUD操作できるので、そちらの方がいいかも？(model.mdを参照)

#### SELECT
```php
$items = DB::select('select * from people');

$items = DB::select('select * from people where id = :id', $param);
```

#### INSERT
フォーム送信後処理
```php
    public function create(Request $request)
    {
        $param = [
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];
        DB::insert('insert into people (name, mail, age) values (:name, :mail, :age)', $param);
        return redirect('/database');
    }
```

#### UPDATE
フォーム送信前処理  
SELECTでデータをあらかじめ取得しビューに渡す
```php
   public function edit(Request $request)
    {
        $param = ['id' => $request->id];
        $item = DB::select('select * from people where id = :id', $param);
        return view('database.edit', ['form' => $item[0]]);
    }
```

ビューのフォーム（一部抜粋）  
プライマリキーであるIDはフォームにhiddenでいれる
```html
<input type="hidden" name="id" value="{{$form->id}}">
```

フォーム送信後処理  
$form->カラム名　で既存データを入力フォームにはめ込んでいく
```php
    public function update(Request $request)
    {
        $param = [
                'id' => $request->id,
                'name' => $request->name,
                'mail' => $request->mail,
                'age' => $request->age,
        ];
        DB::update('update people set name = :name, mail = :mail, age = :age where id = :id', $param);
    }
```

#### DELETE
```php
   public function remove(Request $request)
    {
        $param = ['id' => $request->id];
        DB::delete('delete from people where id = :id', $param);
    }
```

### クエリビルダ（DBクラス）でのCRUD
クエリビルダを使用する場合、直接SQLを書く必要がない

#### SELECT
get()に引数を渡して、カラム名の指定も可能
```php
$items = DB::table('people')->get();
```
- where(フィールド名, 値)　もしくは　where(フィールド名, 演算記号, 値)  
  WHERE句。where句を複数使用するとAND条件になる。またlike条件も指定可能
- orwhere(フィールド名, 値)　OR条件
- whereRaw(条件式, パラメータ配列)　条件式をそのまま書ける方式　
```php
    public function show(Request $request)
    {
        $min = $request->min;
        $max = $request->max;
        $items = DB::table('people')
            ->whereRaw('age >= ? and age <= ?', [$min, $max])->get();
    }
```
- orderBy(フィールド名, ascまたはdesc)　並び順指定
- offset(整数)　指定した位置からレコードを取得
- limit(整数)　指定した数だけレコードを取得

最後に来るもの
- first()　最初のレコードを返す
- get()　レコードを返す
- toJson()　結果をJSONに変換（モデルインスタンスもJSONに変換してくれる）
- toSql()　発行されたSQLを返す（SQLの実行は行われない）

**全てのSQLの取得**
```php
// SQL保存を有効化
DB::enableQueryLog();

$authors = \App\Author::find([1, 3, 5]);
// クエリの取得
$queries = DB::getQueryLog();

// SQL保存を無効化
DB::disableQueryLog()
```

#### INSERT
フォーム送信後処理
```php
    public function create(Request $request)
    {
        $param = [
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];
        DB::table('people')->insert($param);
    }
```

#### UPDATE
更新画面に行く際にデータを取得しておき、ビュー側に渡す→フォームでIDはhiddenで埋め込むところは、DBクラスでの更新と同じ

whereを挟まずにupdateした場合は、全レコードが更新対象になる
フォーム送信後処理
```php
    public function update(Request $request)
    {
        $param = [
            'id' => $request->id,
            'name' => $request->name,
            'mail' => $request->mail,
            'age' => $request->age,
        ];
        DB::table('people')
            ->where('id', $request->id)
            ->update($param);
    }
```

#### DELETE
```php
    public function remove(Request $request)
    {
        DB::table('people')
            ->where('id', $request->id)->delete();
    }
```
