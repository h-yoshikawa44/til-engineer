## マイグレーション
パス：App\database\migrations
基底クラス：Illuminate\Database\Migrations\Migration(use必要)

データベースに対してSQLを発行する仕組み

マイグレーションの実行状態はmigrationsテーブルで管理されている

### コマンド
#### ファイル生成
例
`php artisan make:migration create_XXX_table`

オプション
- --create=(テーブル名)　新規テーブル作成のためのコードが付与される
- --table=(テーブル名)　指定されたテーブルを操作するためのコードが付与される（テーブル設定の変更などで使用）
- --path=(パス)　指定されたパスにマイグレーションファイルを配置する

#### マイグレーション
実行  
`php artisan migrate`

ロールバック(直前に戻す)  
`php artisan migrate:rollback`

リセット(全てのマイグレーションを元に戻す)  
`php artisan migrate:reset`

### ファイル名
- テーブル新規作成　YYYY_MM_dd_XXXXXX_create_XXX_table

### upメソッド
テーブル生成処理を書く
```php
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('restdata', function (Blueprint $table) {
            $table->increments('id');
            $table->string('message');
            $table->string('url');
            $table->timestamps();
        });
    }
```
Schema::create　テーブル作成

フィールド
- $table->increment(フィールド名)　プライマリキー　自動インクリメントのINT型
- $table->integer(フィールド名)
- $table->BigInteger(フィールド名)
- $table->float(フィールド名, 最大桁数, 小数点の右側桁数)
- $table->double(フィールド名, 最大桁数, 小数点の右側桁数)
- $table->char(フィールド名, サイズ)
- $table->string(フィールド名, サイズ)
- $table->text(フィールド名)
- $table->longText(フィールド名)
- $table->boolean(フィールド名)
- $tabke->json(フィールド名)
- $table->date(フィールド名)
- $table->dateTime(フィールド名)
- $table->softDeletes()　ソフトデリートのためのdeleted_at
- $table->timestamp(フィールド名)
- $table->timestamp()　タイムスタンプ（created_atとmodified_at）

フィールドに属性を与えるメソッド  
上記の定義にメソッドチェーンで続けて記述

例
```php
$table->string('name', 50)->nullable();
```

- after(フィールド名)　引数で指定したフィールドの直後に配置（MySQLのみ）
- nullable()　NULL許容
- default(デフォルト値)　デフォルト値の指定
- unsigned()　数値型のフィールドを符号なしにする

インデックスの付与や削除
- primary(フィールド名)　プライマリキー
- primary([フィールド名１, フィールド名２])　復号プライマリキー
- unique(フィールド名)　ユニークキー
- index(フィールド名)　通常のインデックスを付与
- index(フィールド名, キー名)　キー名を指定して通常のインデックスを付与
- dropPrimary(プライマリキー名)　プライマリキー削除する
- dropUnique(ユニークキー名)　ユニークキーを削除する
- dropIndex(インデックス名)　通常のインデックスを削除する


### downメソッド
```php
    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('restdata');
    }
```
Schema::drop　テーブル削除
$schema::dropIfExists(テーブル名)　テーブルがあれば削除