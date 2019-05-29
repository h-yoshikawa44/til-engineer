## バッチ

コマンドを用意して、それをcronで定期実行するよう登録する

### cronコマンド
実行スケジュール　実行コマンド

スケジュール（左端から）
分 = * もしくは 0-59
時 = * もしくは 0-23
日 = * もしくは 1-31
月 = * もしくは 1-12
曜日 = * もしくは 0-7(0=日曜日)

例（毎日午前5時にコマンド実行）
0 5 * * * /usr/bin/php /path/to/artisan app:send-orders `date --date 'yeasterday' +%Y%m%d`

### crontabコマンド
あらかじめファイルにコマンドを記述しておき、それを指定する方法

例（crontab.txtにコマンドが記述してあるとする）
crontab crontab.txt

### ログ出力
処理結果をリアルタイムで確認できないため、ログに実行状況を出力するようにする

主な個所
- バッチ処理開始時
- バッチ処理終了時
- 外部システムとの通信時


ログファイルの出力先を変えたい場合はサービスプロバイダでバインドを実装して、定義しなおす
(新たにサービスプロバイダを作成した場合はconfig/app.phpに登録を忘れない)

※この方法では出力先が変わらなかった（5.6からログの仕組みが変わった影響？）
```php
   public function register()
    {
        // SendOrdersCommandクラスの生成方法をバインド
        $this->app->bind(SendOrdersCommand::class, function() {
            $useCase = app(SendOrdersUseCase::class);
            // ロガーのファイル設定を変更
            $logger = app('log');
            $logger->useFiles(storage_path() . '/logs/send-orders.log');

            return new SendOrdersCommand($useCase, $logger);
        })
    }
```