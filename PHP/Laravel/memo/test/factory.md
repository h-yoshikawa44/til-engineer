## Factory
パス：App\database\factories

ダミーデータの定義を記述しておく
Eloquentクラスごとのファクトリーを記述することで、シーダーで利用するダミーデータを簡単に生成できる

### 使い方
1. モデルクラスを作成
2. ファクトリークラスを作成し、データ投入処理を定義する
3. シーダークラスのrunメソッドに、ファクトリークラスの利用処理を追加
4. DatabaseSeederクラスのrunメソッドで3の処理を呼び出す

データ投入処理の例
factory
```php
use Faker\Generator as Faker;

$factory->define(App\Bookdetail::class, function(Faker $faker) {
    $faker->locale('ja_JP');
    $now = \Carbon\Carbon::now();
    return [
        'isbn' => $faker->isbn13,
        'published_date' => $faker->date($format = 'Y-m-d', $max = 'now'),
        'price' => $faker->randomNumber(4);
        'created_at' => $now,
        'updated_at' => $now
    ];
});
```

seeder
```php
use Illuminate\Database\Seeder;

class BookdetailsTableSeeder extends Seeder
{
    public function run()
    {
        factory(\App\Bookdetail::class, 50)->create();
    }
}
```