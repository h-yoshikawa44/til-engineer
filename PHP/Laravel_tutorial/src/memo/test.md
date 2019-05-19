## テスト

### ユニットテスト
テスト実行は`vendor/bin/phpunit`  
テストで使用するデータベースは`phpunit.xml`に以下を追加  
```xml
※<php>の個所に追加
<server name="DB_DATABASE" value="test"/>
```

テストデータ定義ファイルは、app/database/factories  
(データ一つ一つというより、サンプルデータを作るための設定定義)
```php
$factory->define(User::class, function (Faker $faker) {
    return [
        'name' => $faker->name,
        'email' => $faker->unique()->safeEmail,
        'email_verified_at' => now(),
        'password' => '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', // password
        'remember_token' => Str::random(10),
    ];
});
```

テストファイルは、app/tests/Feature

- assertTrue(値)　trueかどうか
- assertFalse(値)　falseかどうか
- assertEquals(値1, 値2)　同じ値かどうか
- assertNotEquals(値1, 値2)　違う値かどうか
- assertLessThan(値1, 値2)　第2引数の方が小さいか
- assertLessThanOrEqual(値1, 値2)　第2引数の方が小さいか等しいか
- assertGreaterThan(値1, 値2)　第2引数の方が大きいか
- assertGreaterThanOrEqual(値1, 値2)　第2引数の方が大きいか等しいか
- assertEmpty(値)　空かどうか
- assertNotempty(値)　空でないかどうか
- assertNull(値)　nullかどうか
- assertNotNull(値)　nullでないかどうか
- assertStringStartsWith(値1, 値2)　引数の文字列が指定の文字列で始まるかどうか（第2引数に調べる文字列を指定）
- assertStringEndsWith(値1, 値2)　引数の文字列が指定の文字列で終わるかどうか　

#### webページへのアクセスのテスト
```php
    $response = $this->get('/');
    $response->assertStatus(200);
```

認証が必要なページへのテスト
```php
    $user = factory(User::class)->create();
    $response = $this->actingAs($user)->get('/hello');
    $response->assertStatus(200);
```

#### データベースのテスト
`use RefreshDatabase;`で自動的にマイグレーションなどのセットアップが実行される

```php
        // ダミーで利用するデータ
        // 明示的にテストデータを上書きして指定
        factory(User::class)->create([
            'name' => 'AAA',
            'email' => 'BBB@CCC.COM',
            'password' => 'ABCABC',
        ]);
        factory(User::class, 10)->create();

        $this->assertDatabaseHas('users', [
            'name' => 'AAA',
            'email' => 'BBB@CCC.COM',
            'password' => 'ABCABC',
        ]);
```
