## イベント

- イベント...何かしらの動作や変更などが発生した際に発信されるもので、発生時の情報をオブジェクトとして表現する
- リスナー...イベントに対応する処理を実行する機能。サーバサイドで同期的に処理するか、キューと組み合わせて非同期でも実行可能
- ディスパッチャー...イベントを発行する機能。リスナークラスの実装次第でサーバサイドでリスナーを起動させるか、socket.io(websocket)を通じてWebブラウザに実行させるかディスパッチャーが振り分ける

### event:generateコマンドによるイベントクラス作成(イベントとリスナーをまとめて作成)
App\Providers\EventServiceProviderクラスのlistenプロパティに記述したイベントクラス、リスナークラスを作成する

```php
namespace App\Providers;

use Illuminate\Support\Facades\Event;
use Illuminate\Foundation\Support\Providers\EventServiceProvider as ServiceProvider;

class EventServiceProvider extends ServiceProvider
{
    protected $listen = [
        'Eventクラス' => [
            'Eventに対応したListnerクラス'
        ],
    ];
}
```

`php artisan event:generate`コマンドで生成

### イベントとリスナーを別々で作成
#### make:eventコマンドによるイベントクラス作成
例
`php artisan make:event App\Events\PublishProcessor`

イベントクラスのひな型に含まれるトレイト
- Illuminate\Queue\SerializesModels  
  Queueと組み合わせて非同期イベントを実行するときに利用
- Illuminate\Foundation\Events\Dispatchable  
  イベントクラスにDispatcherとして作用させるときに利用
- Illuminate\Broadcasting\InteractsWithSockets  
  socket.ioを使ってブラウザにイベント通知するときに利用

#### イベントリスナーのひな型の生成
例（--eventでイベントクラス名を指定）
`php artisan make:listener MessageSubscriber --event PublishProcesssor`

#### サービスプロバイダに登録
App\Providers\EventServiceProviderクラスを利用するのが一般的  
なお、ひとつのイベントに対して複数のリスナーを登録可能

リスナークラスで任意のメソッドを実行させたい場合は「Listenerクラス名@メソッド名」で指定

listenプロパティで登録する例
```php
    protected $listen = [
            PublishProcessor::class => [
            MessageSubscriber::class,
        ],
    ];
```

bootメソッドで登録する例
```php
    public function boot()
    {
        parent::boot();

        // Facadeを利用した例
        Event::listen(
            PublishProcessor::class,
            MessageSubscriber::class
        );
        // フレームワークのDIコンテナにアクセスする場合
        $this->app['events']->listen(
            PublishProcessor::class,
            MessageSubscriber:::class
        );
    }
```

### 登録したイベントの使い方
イベント発火は、Eventファザードなどからdispatchメソッドを利用する  
dispatchの第1引数にlistenメソッドで指定したイベントのインスタンスまたは文字列（文字列指定の場合は、第2引数でリスナークラスに渡したい値を指定する

```php
Route::get('/', function() {
    $view = view('welcome');
    // Dispatcherクラス経由でEventを実行する場合
    \Event::dispatch(new \App\Events\PublishProcessor(1));
    return $view;
});
```

### イベントのキャンセル
hasListener...指定したイベントに紐づいたリスナーがあるかどうか
forget...対象のイベントを指定して、リスナーの起動をキャンセル

キャンセル後はdispatchで読んでも、リスナーの処理は実行されない

```php
namespace App\Service;

use App\Entity\Customer;
use App\Events\PublishProcessor;
use Illuminate\Support\Facades\Event;

class Order
{
    const DISABLE_NOIFICATION = 1;

    public function run(Customer $customer)
    {
        if ($customer->getStatus() === self::DISABLE_NOTIFICATION) {
            if (\Event::hasListener(PublishProcessor::class)) {
                \Event::forget(PublishProcessor::class);
            }
        }
        \Event::dispatch(new PublishProcessor($customer->getId()));
    }
}
```

### 非同期リスナーの起動
`php artisan queue:work`  
※defalutキューになるので、他のキューの動作を見る場合は``--queue`オプションで指定する

Queueを使用した非同期リスナークラスは`use InteractsWithQueue;`をつけて実装

ドライバの選択は.envファイルとconfig/queue.phpで指定

Redisを使用する場合はインストールする必要がある  
`composer require predis/predis`  
Syncを使用する場合は同期処理になる（テストやデバッグで使用）

### Queue
#### jobクラス

Knp\Snappy\Pdfクラスがサービスコンテナに登録してあるとする

ジョブクラス例
```php
namespace App\Jobs;

use Illuminate\Bus\Queueable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;
use Knp\Snappy\Pdf;

class PdfGenerator implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;

    private $path = '';

    public function __construct(string $path)
    {
        $this->path = $path;
    }

    // handleメソッドの引数に型宣言を記述すると、サービスコンテナで定義したオブジェクトが渡される
    public function handle(Pdf $pdf)
    {
        $pdf->generateFromHtml(
            '<h1>Laravel</h1><p>Sample PDF Output.</p>', $this->path
        );
    }
}
```

ジョブ実行例
```php
namespace App\Http\Controlllers;

use App\Jobs\PdfGenerator;
use Illuminate\Contracts\Bus\Dispatcher;

class PdfGeneratorController extends Controller
{
    public function index()
    {
        $generator = new PdfGenerator(storage_path('pdf/sample.pdf'));
        // dispatchヘルパ関数で実行指示
        dispatch($generator);
    }

    // インタフェースを記述し、メソッドインジェクションでインスタンス生成を行う場合
    public function methodInjectExample(Dispatcher $dispatcher)
    {
        $generator = new PdfGenerator(storage_path('pdf/sample.pdf'));
        // dispatchメソッドで実行指示
        // Busファザードを使って記述も可能
        $dispatcher->dispatch($generator);
    }
}
```

JobクラスにIlluminate\Foundation\Bus\Dispatchableトレイトが記述されていれば、以下の記述方法でも可能
```php
namespace App\Http\Controllers;

use App\Jobs\PdfGenerator;

class PdfGeneratorController extends Controller
{
    public function index()
    {
        PdfGenerator::dispatch(storage_path('pdf/sample.pdf'));
    }
}
```

#### キュー指定による分散処理パターン
複数の処理をキューで実行すると、順番待ちの処理が多く発生してしまう  
異なるキューを指定することで分散して処理を行わせることで、順番待ちを減らす

```php
public function index()
{
    $generator = New PdfGenerator(storage_path('pdf/sample.pdf'));
    // dispatchヘルパ関数でどのQueueで処理を行うか指定
    dispatch($generator)->inQueue('pdf.generator');
}
```

#### キューの遅延処理

```php
public function handle(Registered $event)
{
    dispatch(new SendRegistMail($event->user->mail))
        ->inQueue('mail')
        ->delay(now()->addHour(1));
}
```