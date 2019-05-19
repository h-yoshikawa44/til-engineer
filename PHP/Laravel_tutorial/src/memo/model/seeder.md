#### シーダー
データベースのデータを挿入するためのファイル

 `php artisan db:seed`で実行されるのは、DatabaseSeederであるため、他のSeederを作成した場合は、DatabaseSeederに登録しておく必要がある

 ```php
    $this->call(PeopleTableSeeder::class);
```
