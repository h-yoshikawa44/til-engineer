## エラーハンドリング

### エラー表示
エラー内容がWebブラウザなどの表示画面に表示されるのは、.envのAPP_DEBUGがtrueになっているため。
本番環境ではfalseにすること。

### エラーの種別
Laravelには例外を処理するApp\Exception\Handlerクラスがある。このクラスは補足しきれなかった例外を処理するために利用する。発生した例外に対してリカバリ処理を行わない場合は、このクラスで処理することになる

種別
- システム例外
  処理が実行できない例外を指す。アプリケーションそのものに由来するバグ、依存ライブラリのバグ、データベースやキャッシュサーバなどのハードウェア・ミドルウェアによる障害、ネットワーク障害など

- 不正リクエスト例外
  アプリケーションへの不正なリクエストで発生する例外を指す。そんざいしないURIへのリクエストやバリデーションエラーなどが該当。リクエストに誤りがある場合はその内容を的確に通知する実装が必要。ミドルウェア、フォームリクエストもしくはばりでしょんなどの機能を利用してエラーハンドリングを行う

- アプリケーション例外
  アプリケーションでていぎされる、ビジネスロジックに関連するエラーが該当。ユーザ登録処理での重複エラーや在庫不足など、アプリケーションが定めている正常な処理を続行できない例外は、要件に合わせて的確に処理する必要がある

Handlerクラスでは、発生した例外を記録としてログに書き込むreportメソッドと、エラー発生時にレスポンスを作成するrenderメソッドが用意されている

ただし、以下の例外はreportメソッドで記録できない
- Illuminate\Auth\AuthenticationException
- Illuminate\Auth\Access\AuthorizationExceotion
- Symfony\Component\HttpKernel\Exceotion\HttpException
- Illuminate\Http\Exceotions\HttpResponseExceotion
- Illuminate\Database\Eloquent\ModekNotFounfExceotion
- Illuminate\Session\TokenMismatchException
- Illuminate\Validation\ValidatationException

また、記録の残す必要がない例外はdontReportプロパティで指定することもできる

### 例外の描画テンプレート
アプリケーションに合わせて、エラー発生時に描画されるテンプレートを変更する場合は、以下のパスにファイルを設置する
- 404　resources/views/errors/404.blade.php
- 419　resources/views/errors/419.blade.php
- 429　resources/views/errors/429.blade.php　
- 500　resources/views/errors/500.blade.php
- 503　resources/views/errors/503.blade.php

App\Exceptions\Handlerクラスの挙動
- Illuminate\Auth\AuthenticationException
  401・リクエストヘッダでJSONが指定されている場合は、ステータスコードとエラーメッセージを返却、それ以外の場合は、ルート名がloginと指定されているurlにリダイレクト
- Illuminate\Auth\Access\AuthorizationExceotion
  403・Symfony\Comoponent\HttpKernel\Exception\AccessDeniedHttpExceptionインスタンスに変換
- Symfony\Component\HttpKernel\Exceotion\HttpException
  任意のレスポンスコード・例外発生時にステータスコード指定
- Illuminate\Http\Exceotions\HttpResponseExceotion
  任意のレスポンスコード・Symfony\Component\HttpFoundation\Responseクラス、またはIlluminate\Http\Responseクラスでステータスコード指定
- Illuminate\Database\Eloquent\ModekNotFounfExceotion
  404・Symfony\Component\HttpKernel\Exception\NotFoundHttpExceotionインスタンスに変換
- Illuminate\Session\TokenMismatchException
  419・Symfony\Component\Exceotion\HttpExceptionインスタンスに変換
- Illuminate\Validation\ValidatationException
  422・リクエストヘッダでJSONが指定されている場合は、ステータスコードとエラーメッセージを返却、それ以外の場合は指定したルートにリダイレクト

このほか、Symfony\Component\HttpKernel\Exception\HttpExceptionインスタンスで500が返却される

カスタムヘッダを利用したエラーレスポンス実装例
```php
namespace App\Exceptions;

use Exception;
use Illuminate\Database\QueryException;
use Illuminate\Http\Response;
use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;

class Handler extends ExcetionHandler
{
    // 省略
    public function render($request, Exception $exception)
    {
        // 送出されたExceptionクラスを継承したインスタンスのうち特定の例外のみ処理を変更
        if ($exception instanceof QueryException) {
            // カスタムヘッダを利用して、エラーレスポンス、ステータスコード500を返却
            return new Response('', Response::HTTP_INTERNAL_SERVER_ERROR, [
                'X-App-Message' => 'An error occurred.'
            ]);
        }
        return parent::render($request, $exception);
    }
}
```

### エラーハンドリングパターン
#### Bladeテンプレートと例外処理
Illuminate\Contracts\Support\Responsableインタフェースを継承して、toResponseメソッドを実装すると、App\Exceptions\Handlerの親クラスであるIllumimate\Foundation\Exceptions\Handlerクラスのrenderメソッドで、例外クラスに合わせたレスポンスを返却する

toResponseメソッドはSymfony\Component\HttpFoundation\Responseクラスを継承したインスタンスを返却する必要があるが、例外出力処理を関連付けることができる

```php
pulic function toResponse($request): Response
{
    return new Response(
        $this->factory->with($this->error, $this->message);
    )
}
```


例外処理とレスポンスを結び付ける例
```php
public function index()
{
    // 第1引数で指定したテンプレートに第2引数で指定したメッセージを渡す
    throw new App\Exceptions\AppException(view('errors.page'), 'error.');
}
```

#### APIレスポンスと例外処理
REST APIに採用されることの多いHyper Application Languageと互換性を持つvnd.errorを採用する例はApp\Exceptions\UserResourceExceptionを参照
