## artisan（アーティザン）コマンド
- 各ファイル作成  
  `php artisan make:(ファイル種類)　ファイル名`  
  例 `php artisan make:controller HelloController`

  - contoroller(app/Http/Controllers)
  - provider(app/Providers)
  - middleware(app/Http/Middleware)
  - request(app/Http/Requests)
  - migration(database/migrations)
  - seeder(database/seeds)
  - model(app)
  - test(app/tests/Feature)
  - factory(app/database/factories)

オプション
- `--resource`　コントローラでCRUDのメソッドを用意する

- migrate実行  
  `php artisan migrate`

- seederを実行  
  `php artisan db:seed`