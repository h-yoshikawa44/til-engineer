## コマンド

コマンドを実装するには、クロージャで実装する方法とコマンドクラスを実装する方法がある  

### クロージャで実装
クロージャの場合はroutes/console.phpに記述する

クロージャの例
```php
Artisan::command('hello:closure', function() {
    $this->comment('Hello closure command');  // 文字列出力
})->describe('サンプルコマンド（クロージャ）');  // コマンド説明
```

コマンド実行  
`php artisan hello:closure`

### コマンドクラスで実装
例
`php artisan make:command HelloCommand`

$signature...コマンド名の指定
$description...コマンドの説明
handleメソッド...実行する処理

#### 引数
コマンドに引数を渡すことも可能
```php
protected $signature = 'hello:class {name}';
```

使用する際はargumentで取り出せる
（ただし、この場合はコマンド使用時に引数を省略できない）
```php
public function handle()
{
    $name = $this->argument('name');
    $this->comment('Hello ' . $name);
}
```

コマンド引数の指定方法
- {name}　引数を文字列として取得。省略するとエラー
- {name?}　引数を文字列として取得。省略可能
- {name=default}　引数を文字列として取得。省略すると=の右辺がデフォルト値になる
- {name*}　引数を配列として取得。省略するエラー
- {name : description}　:以降に説明を記述できる。コロンの前後にスペースが必要

#### オプション引数
```php
protected $signature = 'hello:class {--switch}
```

使用する場合はoptionメソッドで取得
```php
public function handle()
{
    $switch = $this->option('switch');
    $this->comment('Hello ' . ($switch ? 'ON' : 'Off'));
}
```

オプション引数の指定方法
- {--switch}　引数を論理値として取得。指定するとtrue、省略するとfalseになる
- {--switch=}　引数を文字列として取得。省略可能
- {--switch=default}　引数を文字列として取得。省略すると=の右辺がデフォルト値になる
- {--switch=*}　引数を配列として取得。実行時に --switch=1 --switch=2 と指定すると['1', '2']といった配列になる
- {--switch : description}　:以降に説明を記述できる。コロン前後にスペースが必要

#### コマンドからの出力
主な出力メソッド
- line($string, $style=null, $verbosity=null)　スタイル無し
- info($string, $verbosity=null)　infoスタイル（文字色：緑）
- comment($string, $verbosity=null)　commentスタイル（文字色：黄）
- question($string, $verbosity=null)　questionスタイル（文字色：黒、背景色：シアン）
- error($string, $verbosity=null)　errorスタイル（文字色：白、背景色：赤）
- warn($string, $verbosity=null)　warnスタイル（文字色：黄）

$verbosityには出力レベルを指定
- VERBOSITY_QUIET　定数値：16　常に出力
- VERBOSITY_NORMAL　定数値：32　デフォルトの出力レベル。--quiet以外で出力
- VERBOSITY_VERBOSE　定数値：64　-v, -vv, -vvvで出力
- VERBOSITY_VERY_VERBOSE　定数値：128　-vv, -vvvで出力
- VERBOSITY_DEBUG　定数値：256　-vvvでのみ出力

例
```php
$this->info('quiet', OutputInterface;;VERBOSITY_QUIET);
```

#### アプリケーションからコマンドの実行
Artisanファザードのcallメソッドを利用する

```php
// 引数なし
Route::get('/no_args', function() {
    Artisan::call('no-args-command');
});

// 引数あり
Route::get('/with_args', function() {
    Artisan::call('with-args-command', [
        'arg' => 'value',
        '--switch' => false
    ]);
});
```

Artisanファザードを利用しない場合
```php
use Illuminate\Contracts\Console\Kernel;

Route::get('/no_args_di', function(Kernel $artisan) {
    $artisan->call('no-atgs-command');
});
```

コマンドから他のコマンドを呼び出す場合
```php
public function handle()
{
    $this->call('other-command');
}
```

### コマンドエラーのスタックトレース
artisanの各コマンド実行では、処理中に例外が発生しても、例外のメッセージと送出されたファイルおよび行番号しか出力されない  
-vなどのオプションをつけることでスタックトレースも出力可能

### ファイルを分けて処理を分ける
各々の役割が明確で独立したクラスは再利用性が高まるので、別の個所から利用することが容易になる
また、テストコードの記述も容易になる

例
コマンド受付
↓　↑
ExportOrdersCommandクラス
↓　↑
ExportOrdersUseCaseクラス
(ジェネレータをforeachで回し、値をTSV形式に設定)
↓　↑
ExportOrdersServiceクラス  
(DB接続、ジェネレータ取得)