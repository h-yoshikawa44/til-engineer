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

DBファザードを使用するパターンと、その実体であるIlluminate\Databases\Connectionから取得する方法がある

DBファザード
```php
$query = DB::table('books');
```

コネクション
```php
$db = Illuminate\Foundation\Application::getInstance()->make('db');
$connection = $db->connection();
$query = $connection->table('books');
```

#### SELECT
get()に引数を渡して、カラム名の指定も可能
```php
$items = DB::table('people')->get();
```

select系
- select([取得するフィールド名...])
- selectRaw(式)　SQL形式でそのまま書ける

where系
- where(フィールド名, 値)　もしくは　where(フィールド名, 演算記号, 値)  
  WHERE句。where句を複数使用するとAND条件になる。またlike条件も指定可能
- orwhere(フィールド名, 値)　OR条件
- whereRaw(条件式, パラメータ配列)　SQLの条件式をそのまま書ける方式　
```php
    public function show(Request $request)
    {
        $min = $request->min;
        $max = $request->max;
        $items = DB::table('people')
            ->whereRaw('age >= ? and age <= ?', [$min, $max])->get();
    }
```
- whereBetween(フィールド名, 範囲)　指定した範囲
- whereNotBetween(フィールド名, 範囲)　指定した範囲以外
- whereIn(フィールド名, 条件値)　inの条件指定
- whereNotIn(フィールド名, 条件値)　not inの条件
- whereNull(フィールド名)　is nullの条件指定
- whereNotNull(フィールド名)　is not nullの条件指定

where系の条件指定はメソッドチェーンで繋いでAND条件になる  
OR条件にする場合は書く条件指定の頭にorをつける

limitとoffset
- offset(整数)　もしくは　skip(整数)　指定した位置からレコードを取得
- limit(整数)　もしくは　take(整数)　指定した数だけレコードを取得

集計系
- orderBy(フィールド名, ascまたはdesc)　並び順指定
- groupBy(フィールド名)　グルーピング化
- having(フィールド名, 比較演算子, 条件値)　havingの絞り込み
- havingRaw(条件式)　havingを使用したSQLをそのまま書ける方式

テーブル結合
- join(結合テーブル, 自テーブルフィールド名, '=', 結合するテーブルフィールド名)　innerJoin
- leftJoin(結合するテーブル, 自テーブルフィールド名, '=', 結合するテーブルフィールド名)　
- rightJoin(結合するテーブル, 自テーブルフィールド名, '=', 結合するテーブルフィールド名)

クエリの実行（最後に来る）
- first()　最初のレコードを返す
- get()　全てのレコードを返す
- toJson()　結果をJSONに変換（モデルインスタンスもJSONに変換してくれる）
- count()　データの件数
- max(フィールド名)　フィールドの最大値
- min(フィールド名)　フィールドの最小値
- avg(フィールド名)　フィールドの平均値

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

truncate()による全行削除
```php
DB::table('people')->truncate();
```

#### トランザクションとテーブルロック
この処理はEloquentを利用したデータ操作でも可能

トランザクション
- DB::beginTransaction()　手動でトランザクション開始
- DB::rollback()　手動でロールバック実行
- DB::commit()　手動でトランザクションをコミット
- DB::transaction(クロージャ)　クロージャの中でトランザクションを実施

悲観的テーブルロック
- sharedLock()　selectされた行に共有ロックをかけ、トランザクションコミットまで行進を禁止する
- lockForUpdate()　selectされた行に排他ロックをかけ、トランザクションコミットまで読み取りと更新を禁止する