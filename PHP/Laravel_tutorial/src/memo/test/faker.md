## Faker

ダミーデータを作成するためのライブラリ  
Laravelでは標準で搭載されている

### ダミーデータの種類
- name　氏名
- email　メールアドレス
- safeEmail　メールアドレス
- password　パスワード
- country　国名
- address　住所
- phoneNumber　電話番号
- company　企業名
- realText()　テキスト
- isbn13
- date(フォーマット, どの日までか) ※引数は省略可能
- randomNumber(桁数)

seederの例
```php
    public function run()
    {
        $faker = Faker\Factory::create('ja_JP');
        $now = \Carbon\Carbon::now();
        for ($i = 0;$i < 10; $i++) {
            $publisher = [
                'name' => $faker->company . '出版',
                'address' => $faker->address,
                'created_at' => $now,
                'updated_at' => $now
            ];
            DB::table('publishers')->insert($publisher);
        }
    }

```

#### Carbon
timestampのダミーデータはCarbonクラスで対応するとよい
```php
'created_at' => \Carbon\Carbon::now()->toDateTimeString()
```

#### リレーションのあるテーブルでの書き方
親：Customer、子：Report
```php
    public function run()
    {
        factory(\App\Customer::class, 2)
        ->create()
        ->each(function ($customer) {
            factory(\App\Report::class, 2)
                ->make()
                ->each(function ($report) use ($customer) {
                    $customer->reports()->save($report);
                });
        });
    }
```