## マイグレーション
パス：App\database\migrations
基底クラス：Illuminate\Database\Migrations\Migration(use必要)

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
- $table->increment(フィールド名)　プライマリキー
- $table->integer(フィールド名)
- $table->BigInteger(フィールド名)
- $table->float(フィールド名)
- $table->double(フィールド名)
- $table->char(フィールド名)
- $table->string(フィールド名)
- $table->text(フィールド名)
- $table->longText(フィールド名)
- $table->boolean(フィールド名)
- $table->date(フィールド名)
- $table->dateTime(フィールド名)
- $table->timestamp()　タイムスタンプ（created_atとmodified_at）

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