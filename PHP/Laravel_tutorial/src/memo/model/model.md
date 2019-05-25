## モデル
パス(namespace)：App
基底クラス：Illuminate\Database\Eloquent\Model(use必要)

※artisanコマンドではApp配下に作成されるがModelフォルダをはさんでもいいかもしれない

テーブル内容を定義したクラス  
命名規則は単数形  
Eloquent(ORM)を用いて、データベース操作を扱いやすくなる

取得されたデータはモデルクラスのインスタンスのコレクションとなる
そのため、モデルクラスで定義したメソッドを使用することも可能

### プロパティ
#### テーブルとの関連付け
テーブル名を複数形で作成し、クラス名をその単数形で作成すると、暗黙的に関連付けられる

また、テーブル名にスネークケースが使用されている場合、クラス名はキャメルケースで定義で紐づけることが可能

明示的に関連付ける場合はプロパティに記述する
```php
namespace App;

use Illuminate\Database\Eloquent\Model;

class Author extends Model
{
    protected $table = 't_author';
}
```

#### プライマリキーの指定
デフォルトはidカラム

明示的に指定する場合はプロパティに記述する
```php
namespace App;

use Illuminate\Database\Eloquent\Model;

class Author extends Model
{
    protected $primaryKey = 'authord_id';
}
```

#### タイムスタンプの定義
デフォルトではcreated_at、updated_atが登録される

必要ない場合はプロパティに記述
```php
namespace App;

use Illuminate\Database\Eloquent\Model;

class Author extends Model
{
    protected $timestamps = false;
}
```

同様に$dateFormatでタイムスタンプのフォーマットを指定可能（デフォルトはY-m-d H:i:s）

#### 編集可能なカラム設定
createメソッドやupdateメソッドで、連想配列でカラム名と値を渡すことでデータ登録ができる（Masss Assighnment）が、脆弱性もあり、デフォルトでは全てのカラムで無効になっている  

使用するにはホワイトリスト形式化ブラックリスト形式化で記述する必要がある

ホワイトリスト形式（編集可能なカラムを設定）
```php
protected $fiilable = [
    'name',
    'kana'
];
```

ブラックリスト形式(編集を認めないカラムを設定)
```php
protected $guarded = [
    'id',
    'created_at',
    'updated_at'
];
```

### モデルクラスによるCRUD

#### SELECT
クエリビルダについては、database.mdを参照

・レコード全取得
```php
$items = Person::all();
```

・findを用いた取得の例（プライマリキーでの検索）  
findOrFailでも同様の取得ができるが、こちらは該当するレコードがない場合にIlluminate\Database\Eloquent\ModelNotFoundExceptionをスローする
```php
$item = Person::find($request->input);
```

・whereを用いた取得の例
```php
$item = Person::where('name', $request->input)->first();
```

・whereXXXを用いた取得の例  
XXXにはカラム名が入る
```php
$item = Person::whereName('山田太郎')->get();
```


・取得したデータでの処理
```php
$authors = \App\Author::all();

// レコード数を取得
$count = $authors->count();

// レコードの絞り込み
$filered_authors = $authors->filter(function ($author) {
    return $author->id > 5;
})
```

#### INSERT
createを使用した例
```php
\App\Author::create([
    'name' => '山田太郎',
    'kana' => 'ヤマダタロウ',
]);
```

インスタンスを作成して、値をセットしてsaveする例
```php
$author = new \App\Author();

$author->name = '山田太郎';
$author->kana = 'ヤマダタロウ'

$author->save();
```

フォーム送信データでの保存例
```php
    public function create(Request $request)
    {
        $this->validate($request, Person::$rules);
        $person = new Person;
        $form = $request->all();
        unset($form['_token']); // テーブル側にないフィールドは削除しておく
        $person->fill($form)->save(); // モデルのプロパティに代入
        return redirect('/person');
    }
```

**データがない場合のみ登録**
```php
$author = \App\Author::firstOrCreate(['name' => '山田太郎']);
```

もしくは

```php
$author = \App\Author::firstOrNew(['name' => '山田太郎']);
$author->save();
```

#### UPDATE
updateメソッドの例
```php
$author = \App\Author::find(1)->update(['name' => '山田太郎']);
```

インスタンスを取得して、値をセットしてsaveする例
```php
$author = \App\Author::find(1);

$author->name = '田中次郎';
$author->kana = 'タナカジロウ';

$auhtor->save();
```

フォーム送信データでの更新例
```php
   public function update(Request $request)
    {
        $this->validate($request, Person::$rules);
        $person = Person::find($request->id);
        $form = $request->all();
        unset($form['_token']);
        $person->fill($form)->save();
        return redirect('/person');
    }
```

#### DELETE
deleteメソッドによる例
```php
    public function remove(Request $request)
    {
        Person::find($request->id)->delete();
        return redirect('/person');
    }
```

destroyメソッドによる例
```php
// id = 1, 3, 5のレコードを削除する
\App\Auhtor::destroy([1, 3, 5]);
```

**論理削除**
deleteメソッドやdestroyメソッドはレコードを物理削除する  
Eloquentではdeleted_atカラムを利用して削除処理が行われた日時を保存し、このカラムがnullでなければ削除済みデータであるとして扱うことが可能

1. 対象のテーブルにdeleted_atカラムを追加する（$table->softDeletes()）
2. EloquentのクラスにIlluminate\Database\Eloquent\SoftDeletesトレイトをuseする

```php
// 削除済みのデータも含めて取得
$books = App\Author::withTrashed()->get();

// 削除済みのレコードのみ取得
$deleted_authors = \App\Author::onlyTrashed()->get();
```



### スコープ  
モデルの範囲を特定するもの

#### ローカルスコープ  
モデル内にメソッドを用意し、必要に応じてメソッドを明示的に呼び出して条件を絞り込むもの。メソッド名の接頭辞に`scope`がつく（呼び出し時にはつかない）  
複数のスコープを組み合わせて使用することも可能

```php
    public function scopeNameEqual($query, $str)
    {
        return $query->where('name,' $str);
    }
```
呼び出し側
```php
$item = Person::nameEqual($request->input)->first();
```

#### グローバルスコープ
そのモデルでのすべてのレコード取得にスコープが適用される  
モデルクラスの初期化専用のメソッドであるbootメソッドに定義する
```php
    protected static function boot()
    {
        parent::boot();

        static::addGlobalScope('age', function (Builder $builder) {
            $builder->where('age', '>', 20);
        });
    }
```

#### スコープクラス
複数のモデルや、その他プロジェクトで使用するような汎用性の高い処理は、スコープクラスに切り出すとよい
```php
namespace App\Scopes;

use Illuminate\Database\Eloquent\Scope;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Builder;

class ScopePerson implements Scope
{
    public function apply(Builder $builder, Model $model)
    {
        $builder->where('age', '>', 20);
    }
}
```

呼び出し側（モデルクラス・グローバル）
```php
    protected static function boot()
        parent::boot();
        static::addGlobalScope(new ScopePerson);
    }
```

### アクセサとミューテータ
カラムに対して固定の編集を加えたいときに用いる

#### アクセサ
get(カラム名)Attributeの名前でメソッドを作成

定義
```php
    public function getKanaAttribute(string $value): string
    {
        // kanaカラムの値を半角カナに変換
        return mb_convert_kana($value, "k");
    }
```

利用
```php
$authors = \App\Author::all();
foreach ($authors as $author) {
    echo $author->kana;
}
```

#### ミューテータ
set(カラム名)Attributeの名前でメソッドを作成

定義
```php
    public function setKanaAttribute(string $value): string
    {
        // kanaカラムの値を全角カナに変換
        $this->attribute['kana'] = mb_convert_kana($value, "KV");
    }
```

利用
```php
$author = \App\Author::find(Input::get('id'));
$author->kana = Input::get('kana');
$author->save();
```

### リレーション
Personクラスの例（主：Person、従：Board）
```php
    public function board()
    {
        return $this->hasOne('App\Board';)
    }
```
リレーションがあると、Personクラスのインスタンス→Boardクラスのインスタンス  
といったようにアクセスできる（hasManyの場合はBoardクラスのインスタンスの配列を持つ（配列であるが、名前は単数形のboard ※参考書では誤ってか複数形になっていたので注意）

Boardクラス側の例
```php
   public function person() // hasManyの時は複数形の名称
    {
        return $this->belongsTo('App\Person');
    }
```

hasOne、belongsTo、hasManyともに第1引数に関連付けるテーブル、第2引数に内部キー、第3引数に外部キーを指定（省略した場合は第2引数は「モデル名_id」、第3引数は「id」となる

リレーションDBのデータの有無での取得
```php
    $hasItems = Person::has('board')->get(); // boardのデータがあるものを取得
    $noItems = Person::doesntHave('board')->get(); // boardのデータがないものを取得
    $param = ['hasItems' => $hasItems, 'noItems' => $noItems];
    return view('person.index', $param);
```

#### withを利用したEagerロード
Board:all()とするところを以下のようにすることで、内部的なDBアクセス回数を減らせる。
（最初にBoardデータを取得し、重複する外部キーをまとめて、それぞれのキーのPersonを取得するようになる。all()の場合は重複外部キーをまとめずに一つずつ関連データを取得するためにアクセス回数が多くなる）
```php
$items = Board::with('person')->get();
```
