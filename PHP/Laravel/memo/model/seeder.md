## シーダー
パス：App\database\seeds
基底クラス：Illuminate\database\Seeder(use必要)

データベースのデータを挿入するためのファイル

 `php artisan db:seed`で実行されるのは、DatabaseSeederであるため、他のSeederを作成した場合は、DatabaseSeederに登録しておく必要がある

 ```php
    $this->call(PeopleTableSeeder::class);
```

### runメソッド
ここにINSERT処理を書いていく  
for文を使ってデータを作成など、データの組み立て方は自由

``` php
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $param = [
            'name' => 'taro',
            'mail' => 'taro@yamada.jp',
            'age' => 12
        ];
        DB::table('people')->insert($param);

        $param = [
            'name' => 'hanako',
            'mail' => 'hanako@flower.jp',
            'age' => 34
        ];
        DB::table('people')->insert($param);

        $param = [
            'name' => 'sachiko',
            'mail' => 'sachiko@happy.jp',
            'age' => 56
        ];
        DB::table('people')->insert($param);
    }
```