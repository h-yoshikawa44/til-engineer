## ファザード

クラスメソッド形式でフレームワークの機能を利用できるもの

ファザードが動く例
1. Config::get('app.debug')がコールされる
2. Configの実態であるIlluminate\Support\Facades\Configクラス（config/app.phpにaliasesが定義されている）のgetメソッドを呼び出す
3. Illminate\Support\Facades\Configクラスにはgetメソッドがないため、スーパークラスの__callStaticメソッドを呼び出す
4. __callStaticメソッドでは、getFacadeRootメソッドで操作対象のインスタンスを取得し、getメソッドを実行する（getFacadeRootメソッドでは、getFacadeAccessorメソッドで取得した文字列をresolveFacadeInstanceメソッドにより、サービスコンテナで解決し、取得したインスタンスを返す）

### Input・Requestファザード
```php
// nameキーでリクエストから値を取得
$name = Input::get('name');

// nameキーがない場合、guestを返す
$name = Input::get('name', 'guest');
```

Inputファザードで、getメソッド以外のメソッドは、結果的にIlluminate\Http\Requestクラスのインスタンスへのアクセスになる

requestファザードではgetメソッドがなく、Illuminate\Http\Requestクラスのインスタンスへのアクセスという点は同じため、ほぼ同じ使い方ができる

```php
// 全ての入力値を$inputsで取得
// (ユーザ入力による値設定がシステムの意図しない項目である可能性がある)
$inputs = Input::all();

// 配列で指定した入力値のみ、値を取り出す
$inputs = Input::only(['name', 'age']);
$name = $inputs['name'];

// アップロードされたファイルを取得し、$contentに読み込む
$file = Input::file('material');
$content = file_get_contents($file->getRealPath());

// クッキーの値を取得
$name = Input::cookie('name');

// ヘッダ値を取得
$acceptLangs = Input::header('Accept-Language');

// 全サーバ値を取得
$serverInfo = Input::server();
```