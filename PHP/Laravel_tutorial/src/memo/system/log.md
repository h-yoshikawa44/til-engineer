## ログ
Laravel 5.6以降では従来のログ実装が変更されて、拡張や多様性を持たせることが容易になっている

Logファザード、またはPsr\Log\LoggerInterfaceインタフェースを実装しているため、インタフェースをサービスコンテナ経由で利用したり、loggerヘルパー関数で利用できたりする

### ログレベル
メソッドとログレベルの対比
- log::debug($message)　debug
- log::info($message)　info
- log::notice($message)　notice
- log::warning($message)　warning
- log::error($message)　error
- log::critical($message)　critical
- log::alert($message)　alert
- log::emergency($message)　emergency

ログ出力
```php
\Log::debug('message', ['user_name' => 'laravel', 'id' => 100]);
```
↓
```
[2019-06-01 14:30:00] local.DEBUG: message {"user_name:"laravel", "id":100}
```

### ログ出力設定
Laravel5.5 までは、single、daily、syslog、errorlogが標準で用意されていたが、5.6以降ではこれに加えて複数のログ出力を行うstack、Slackへの通知を行うslack、アプリケーションに合わせたログドライバを追加するcustomが選択できる

ログの設定は5.5まではconfig/app.phpのlogとlog_levelキーを利用  
5.6以降はconfig/logging.phpを利用する

#### single
標準では、storage/log/laravel.logファイルにログが出力される  
出力ファイルパスは固定であるため、ログ出力が多いサービスやアクセスが多いサービスなどでは、サーバのストレージを圧迫してしまい、空き容量不足などの障害の原因になる。そのため、ログファイルのローテーションが必要になる

#### daily
日単位でログファイルを作成し、指定期間分のログファイルを保持する。  
5.5までは標準で5日間、config/app.phpにlog_maxfilesキーを追加して日数を指定できる  
5.6以降では標準で7日間、config.logging.phpで設定

5.5向け設定
```php
'log' => env('APP_LOG', 'single'),
// ログファイルを残す期間をデフォルトの5日間から7日間へ変更
'log_max_files' => 7,

'log_level' => env('APP_LOG_LEVEL', 'debug'),
```

5.6以降向け設定
```php
'daily' => [
    'driver' => 'daily',
    // ログが出力したいファイルパスを指定
    'path' => storage_path('logs/laravel.log'),
    // ログファイルを残す期間を指定
    'days' => 7,
],
```

#### syslog
ログをsyslogに出力する。標準的なLinux環境での出力先は/var/log/messages

・rsyslogによるログ集約
複数台のサーバで構築されるWebアプリケーションの場合は、rsyslog等を利用してsyslogの集約が可能  

syslogからLaravelアプリケーションのログだけを別ファイルに出力して、他のサーバに転送するケース
（/etc/rsyslog.confまたは/etc/rsyslog.dに設定ファイルを作成）

rsyslogによるLaravelアプリケーションログ設定例
```
# 以下をアンコメントする
$ActionQueueFileName fwdRules1 # Unique name prefix for spool files
$ActionQueueMazDiskSpace lg # Igb spacde limit (use as much as possinble)
$ActionQueueSaveOnShutdown on # save messages to disk on shutdown
$ActionQuwuwType LinkedList # run asynchronously
$ActionResumeRetryCount -1 # infinite retries if host is down

# 以下の設定を加える
# syslogからLaravelアプリケーションログを /var/log/laravel.logに格納したい場合
:programname, isequal, "laravel" /var/log/laravel.log

# syslogからLaravelアプリケーションログをログ集約サーバに送信する
:programname, isequal, "laravel" @@192.168.1.32
```
設定変更後はrsyslogを再起動することで有効になる

rsyslogによるログ集約サーバ設定例
```
# 以下をアンコメント
$modLoad imtcp
$InputTCPServerRun 514

# ログ送信を許可するアドレスを追記
$AllowedSender TCP, 127.0.0.1, 192.168.1.0/24

# ログ集約について、ファイル名のフォーマット指定を追記
$template message_log, "/var/log/laravel/%fromhost%_%$year%%$month%%$day%.log"*.*  -?message_log
```
設定変更後にログ集約サーバを再起動すれば設定が有効になり、他のサーバで稼働しているLaravelアプリケーションのログが格納される

#### errorlog
PHPのerrorlof関数でログ出力を行う  
[PHPのマニュアル](http://php.net/manual/ja/function.error.log.php)参照

#### stack
5.6以降で追加されている設定  
Laravelが利用しているMonologの機能を用いて、config/logging.phpのchannelsで複数のログドライバを指定することで同時に利用できる

#### slack
Slackの指定チャンネルへログ内容を通知する。閾値以上のエラーレベルでなければ通知されない（デフォルトではcritical以上でないと通知されない）

5.5以前のバージョンではサービスプロバイダを利用して追加する必要がある
config/slack.phpに記述する例
```php
return [
    // slackのwebhook urlを記述
    'url' => 'SLACK_WEBHOOK_URL',
    // slackのチャンネルを記述
    'channel' => 'incident',
    'username' => 'Laravel log',
    'emoji' => ':boom:',
    'level' => 'critical',
];
```

SlackWebhookHandlerの追加例
```php
namespace App\Providers;

use Illuminate\Log\Writer;
use Illuminate\Support\ServiceProvider;
use Psr\Log\LoggerInterface;
use Monolog\Handler\SlackWebHookHandler;

// デフォルトのAppServiceProviderを利用しても良い
class LogServiceProvider extends ServiceProvider
{
    public function register()
    {
        // サービスコンテナのLogファザードの本体であるインスタンスを取得
        $logger = $this->app[LoggerInterface::class];
        // Laravelが利用しているMonologのインスタンスを取得し、ログハンドラの追加を行う
        $monolog = $loggergetMonolog();
        // config/slack.phpに設定ファイルを用意し、設定値を取得
        $config = $this->app['config']->get('slack');
        $monolog->pushHandler(new SlackWebhookhandler(
            $config['url'],
            $config['channel'] ?? null,
            $config['username'] ?? Laravel,
            $config['attachment'] ?? true,
            $config['emoji'] ?? ':boom:',
            $config['short'] ?? false,
            $config['contect'] ?? true,
            \Monolog\Logger::CRITICAL
        ));
    }
}
```

サービスプロバイダを登録
```php
'providers' => [
    // 省略
    App\Providers\LogServiceProvider::class,
```