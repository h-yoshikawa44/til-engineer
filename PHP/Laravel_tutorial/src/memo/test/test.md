## テスト
Laravelにはクラスやメソッドなどモジュール単位の動作を保証するユニットテストと
、WebページやAPI機能を検証するフィーチャーテストがサポートされている。  
テストライブラリとしてPHPUnitがある。

### テストの概要
テスト実行は`vendor/bin/phpunit`  
引数にテストファイルを指定することも可能（指定しない場合は全てのテストを実行）

#### テストメソッド
メソッド名の先頭にtestがついているメソッドが、テストメソッドとして実行される
（メソッドのdocコメントに@testをつける方法もある）

テスト対象のメソッド名から始めて、その後ろにテストしたい引数や処理などを日本語で記述すると、テスト内容が分かりやすい


#### テスト用のDBデータ
データベース生成
`mysqladmin create データベース名`

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

#### テストの前処理、後処理
・setUp　各テストメソッドごとの前処理(protected)
・setUpBeforeClass　各テストクラスごとの前処理(public static)
・tearDown　各テストメソッドごとの後処理(protected)
・tearDownAfterClass　各テストクラスごとの後処理(public static)

なお、各メソッドにはparent::setUp()など、親クラスのメソッドを呼ぶようにする

**注意点**
setUpとtearDownに関しては、戻り値の型を指定しないと「must be compatible」とエラーになる（`: void`を指定）
（親クラス側で指定されているため、合わせる必要がある）
```php
    protected function setUp(): void
    {
        parent::setUp();

        echo __METHOD__, PHP_EOL;
    }
```


#### アサーションメソッド
- assertTrue(値)　trueかどうか
- assertFalse(値)　falseかどうか
- assertEquals(値1, 値2)　同じ値かどうか
- assertNotEquals(値1, 値2)　違う値かどうか
- assertSame(値1, 値2)　同じ値かどうか（型も含めて）
- assertInstanceOf(クラス名, インスタンス)　値のクラスが一致するか
- assertLessThan(値1, 値2)　第2引数の方が小さいか
- assertLessThanOrEqual(値1, 値2)　第2引数の方が小さいか等しいか
- assertGreaterThan(値1, 値2)　第2引数の方が大きいか
- assertGreaterThanOrEqual(値1, 値2)　第2引数の方が大きいか等しいか
- assertEmpty(値)　空かどうか
- assertNotEmpty(値)　空でないかどうか
- assertNull(値)　nullかどうか
- assertNotNull(値)　nullでないかどうか
- assertStringStartsWith(値1, 値2)　引数の文字列が指定の文字列で始まるかどうか（第2引数に調べる文字列を指定）
- assertStringEndsWith(値1, 値2)　引数の文字列が指定の文字列で終わるかどうか　
- assertRedExp　値が正規表現にマッチするか
- assertCount　値が配列の場合、要素数が一致するか
- assertArrayHasKey　値が配列の場合、指定したキーが存在するか
- assertFileEquals　ファイルの内容が一致するかどうか
- assertJsonFileEqualsJsonFile　JSONファイルの内容が一致するかどうか

・DB関連
- assertDatabaseHas(テーブル名, [フィールド名 => 値])　テーブルに指定したレコードが存在するか
- assertDatabaseMissing(テーブル名, [フィールド名 => 値])　テーブルに指定したレコードが存在しないか

・HTTPリクエスト関連
- assertStatus(ステータス)　HTTPステータスコードが一致しているか
- assertSuccessful()　HTTPステータスコードが2xxなら成功
- assertRedirect(URI=null)　HTTPステータスコードが201, 301, 302, 303, 307, 308のいずれかで、かつLocationヘッダの値がapp('url')->to($url)の値と一致すれば成功
- assertHeaders(ヘッダ名, 値=null)　レスポンスヘッダが存在(値がにnull指定の場合）、もしくは該当ヘッダの値が一致すれば成功
- assertHeaderMissing(ヘッダ名)　指定したレスポンスヘッダが存在しなければ成功
- assertExactJson(配列, strict=false)　レスポンスボディのJSONをでコードした配列が一致すれば成功
- assertJson(配列, strict=false)　レスポンスボディのJSONをデコードした配列に含まれていれば成功

#### モック
モック生成には、PHPUnitのモック（createMockやgetMockBuilderメソッドなど）やMockeryなどの木琴ぐライブラリを使用する方法がある  
単純なモックであれば無名クラスで対応可能

```php
    // Eloquentクラスのモック化
    $this->customerPointEventMock = new class extends EloquentCustomerPointEvent
    {
        /** @var PointEvent */
        public $pointEvent;

        public function register(Pointevent $event)
        {
            $this->pointEvent = $event;
        }
    };
```

コンポーネントのモック  
フレームワークが提供するMail、Event、Notification、Bus、Queue、Storageにはそれぞれコンポーネントと同じAPIを持つモッククラスが用意されている。またモッククラスにはアサーションメソッドも備わっているので、送信された内容も検証できる

#### Carbonクラス
日時のデータを作成するのに使用するクラス
- Carbon::now()　現在日時

### フィーチャーテスト
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

・callメソッドでHTTPリクエスト再現
public function call(
    $method,  // HTTPメソッド
    $uri,  // URI
    $parameters = [], // 送信パラメータ
    $cookies = [],  // cookie
    $files = [],  // アップロードファイル
    $server = [],  // サーバパラメータ
    $content = null  // RAWリクエストボディ
)

```php
$response = $this->call('GET', '/api/get?class=motogp&no=99');

$response = $this->call('GET', '/api/get', [
    'class' => 'motogp',
    'no' => '99'
]);
```

・jsonメソッドでJSONリクエスト再現
public function json($method, $uri, array $data = [], array $headers = [])

jsonメソッドではリクエストヘッダが自動的に設定される
- Content-Length　JSONデータサイズ（byte)
- Content-Type　application/json
- Accept　aplication/json

・callメソッドやjsonメソッドをラップしたメソッド
- get($uri, $headers = [])　GETリクエスト
- getJson($uri, $headers = [])　GETリクエスト（JSON）
- post($uri, $data = [], $headers = [])　POSTリクエスト
- postJson($uri, $data = [], $headers = [])　POSTリクエスト（JSON)
- put($uri, $data = [], $headers = [])　PUTリクエスト
- putJson($uri, $data = [], $headers = [])　PUTリクエスト（JSON)
- patch($uri, $data = [], $headers = [])　PATCHリクエスト
- patchJson($uri, $data = [], $headers = [])　PATCHリクエスト（JSON)
- delete($uri, $data = [], $headers = [])　DELETEリクエスト
- deleteJson($uri, $data = [], $headers = [])　DELETEリクエスト（JSON）

・ミドルウェアの無効化
withoutMiddlewareメソッドを使用する
引数を指定しないと、全ミドルウェアを無効にする（use withoutMiddlewareを使用しても同様）
```php
$response = $this->withoutMiddlewarer(TeaPotMiddleware::class)
                ->getJson('/api/live');
$response->assertStatus(200);
```

### ユニットテスト
#### データプロバイダ
引数や戻り値などパラメータのみを変更してテストする場合に用いる。
データプロバイダを利用することで、同じテストコードに対して異なるパラメータを与えるテストを実行できる。

```php
// データプロバイダはpublicにすること
public function dataProvider_for_calcPoint(): array
{
    return [
        // エラー時にそのデータのキーが表示されるのでわかるようにしておくとよい
        '購入金額が0なら0ポイント' => [0, 0],  // 引数0, 0をテストメソッドに渡す
        '購入金額が999なら0ポイント' => [0, 999],
        '購入金額が1000なら10ポイント' => [10, 1000]
    ];
}
```

利用
```php
/**
 * @test
 * @dataProvider dataProvider_for_calcPoint
 */
public function calcPoint(int $expcted, int $amount)
{
    $resutl = CalculatePointService::calcPoint($amout);

    $this->assertSame($expcted, $result);
}
```

#### データベースのテスト
`use RefreshDatabase;`で自動的にマイグレーションなどのセットアップが実行される（内部的にはartisan migrate:refresshコマンドを実行して、現在のDBを全てクリアにしてからサイドマイグレーションを実行する）また、トランザクションも実行される

そのほか
- Illuminate\Foundation\Testing\DatabaseMigration  
  テストメソッド実行ごとにマイグレーションのリセット・実行・ロールバックを繰り返す
- Illuminate\Foundation\Testing\DatabaseTransactions  
  テストメソッド実行前に自動でトランザクションを実行する

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

#### 例外発生時のテスト
・try~catchを利用する例
```php
    /**
     * @test
     */
    public function exception_try_catch()
    {
        try {
            throw new \InvalidArgumentException('message', 200);
            $this->fail();  // 例外がスローされない場合はテストを失敗させる
        } catch (\Throwable $e) {
            // 指定した例外クラスがスローされているか
            $this->assertInstanceOf(\IntvalidArgumentException::class, $e);
            // スローされた例外のコードを検証
            $this->assertSame(200, $e->getCode());
            // スローされた例外のメッセージを検証
            $this->assertSame('message', $e->getMessage());
        }
    }
```

・expectedExceptionを利用する例
expectException、expectExceptionCode、expectExceptionMessage、これらのメソッド実行後に対象コードを実行すると、スローされた例外に対して検証が行われる。検証に失敗した場合や例外がスローされなかった場合にエラーになる
```php
   /**
     * @test
     */
    public function exception_expectedException_method()
    {
        // 指定した例外クラスがスローされているか
        $this->expectException(\InvalidArgumentException::class);
        // スローされた例外のコードを検証
        $this->expectExceptionCode(200);
        // スローされた例外のメッセージを検証
        $this->expectExceptionMessage('message');

        throw new \InvalidArgumentException('message', 200);
    }
```

・@expctedExceptionアノテーションを利用する例
```php
    /**
     * @test
     * @expectedException \InvalidArgumentException
     * @expectedExceptionCode 200
     * @expectedExceptionMessage message
     */
    public function exception_expectedException_annotation()
    {
        throw new \InvalidArgumentException('message', 200);
    }
```