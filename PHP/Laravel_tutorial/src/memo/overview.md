## 教材・参考
- [PHPフレームワーク Laravel入門](https://www.amazon.co.jp/dp/B07CLLW4MX/ref=cm_sw_r_tw_awdb_c_x_AV43CbJQWQQE6)
- [PHPフレームワーク Laravel](https://www.amazon.co.jp/dp/4802611846/ref=cm_sw_r_tw_awdo_c_x_Ciu4CbTTXNA8A)


## Webページ表示の流れ
HTTPリクエスト
↓　↑
エントリポイント
(public/index.php)
行きはHTTPリクエストを受けてIlluminate\Http\Requestを生成
戻りはIlluminate\Http\ResponseもしくはIlluminate\Http\JsonResponseをうけてHTTP Responseとして出力
↓　↑
HTTPカーネル
(app/Http/Kernel.php)
↓　↑
ルーティング（routes/web.php）
```php
Route::get('hello', 'HelloController@index');
```
※リクエスト情報＋フォームリクエスト（バリデーション）
↓　↑
ミドルウェア（アクション前、アクション後）
（$next実行。複数ミドルウェアがある場合は次のミドルウェアに。
　ない場合は、コントローラーのアクションへ）
↓　↑
コントローラ　⇔　モデル
（app/Http/Controllers/各コントローラ）
Illuminate\Http\ResponseもしくはIlluminate\Http\JsonResponseを返す
```php
return view('hello.index', $data);
```
レンダリング（＋ビューコンポーザ）
↓　↑
ビュー（resources/views/各ビューフォルダ/各ビューファイル）

### MVC
M...Model
V...View
C...Controller

現在のWebアプリケーションのサーバサイドで用いられるMVCは、もともとのMVCパターンをサーバサイドに適用したものでMVC2と呼ばれる。モデルからビューに対して変更を通知する、もともとのMVCにあった処理はサーバサイドにはない。

このMVCパターンは、サーバサイドアプリケーションとして実装するビジネス要求やサービス仕様、ビジネスロジックをモデルとする。また、画面やレスポンスなどの情報を出力するものをビュー、リクエストに応じて処理を振り分けるものをコントローラとして、3つの責務に分割したアプリケーション設計パターン。

### ADR
A...Action
D...Domain
R...Respoder

元来のMVCをもとにサーバサイドへ適用したMVC2と同様、MVCをネットワーク上のリクエスト・レスポンスを扱うサーバサイドアプリケーション向けに、より洗練された設計パターン

・アクション（MVCでいうコントローラ）  
ドメインとレスポンダの接続を行う。HTTPリクエストからドメインを呼び出し、HTTPレスポンスを構築するために必要なデータをレスポンダに渡す  
コントローラクラスが複数のアクションに対応するのに対して、1つのアクションにのみ対応させてアクションクラスとして独立させ、1つのアクションとルートをたいおうさせることで、複雑化を防ぎシンプルにHTTPリクエストを扱い、レスポンダにレスポンス内容を構築を移譲する

・ドメイン（MVCでいうモデル）  
アプリケーションのコアを形成するビジネスロジックへの入り口で、トランザクションスクリプトやドメインモデルなどを利用してビジネスロジックを解決する

・レスポンダ（MVCでいうビュー）  
アクションから受け取ったデータからHTTPレスポンスを構築するプレゼンテーションロジックを解決する。HTTPステータスコードやヘッダー、クッキー、テンプレートを扱うHTML出力、APIなどにおけるJSON変換などを扱う