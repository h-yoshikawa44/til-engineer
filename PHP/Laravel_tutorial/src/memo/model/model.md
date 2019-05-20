## モデル
パス(namespace)：App
基底クラス：Illuminate\Database\Eloquent\Model(use必要)

※artisanコマンドではApp配下に作成されるがModelフォルダをはさんでもいいかもしれない

テーブル内容を定義したクラス  
命名規則は単数形  
Eloquent(ORM)を用いて、データベース操作を扱いやすくなる

取得されたデータはモデルクラスのインスタンスのコレクションとなる
そのため、モデルクラスで定義したメソッドを使用することも可能

### モデルクラスによるCRUD

#### SELECT
レコード全取得
```php
$items = Person::all();
```

findを用いた取得の例（プライマリキーでの検索）
```php
$item = Person::find($request->input);
```

whereを用いた取得の例
```php
$item = Person::where('name', $request->input)->first();
```

#### INSERT
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

#### UPDATE
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
```php
    public function remove(Request $request)
    {
        Person::find($request->id)->delete();
        return redirect('/person');
    }
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

### 値の保護
IDなどのオートインクリメントの値など、DB側で値を用意するものは、入力の保護が可能
```php
// 値を用意しておかないカラム
protected $guarded = array('id');
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
   public function person()
    {
        return $this->belongsTo('App\Person');
    }
```

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
