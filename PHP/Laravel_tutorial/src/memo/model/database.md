## データベース
接続設定はconfig/database.php

### DBクラスでのCRUD
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

### クエリビルダ（DBクラス）
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
