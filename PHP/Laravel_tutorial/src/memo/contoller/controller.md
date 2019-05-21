## コントローラ
パス(namespace)：app\Http\Controllers
基底クラス：App/Http/Controllers/Controller(useは不要)

HTTPリクエストに対応する処理を実行する  
処理が完了すればResponseを生成して返す

### 構文
基本構文（ビューを返す）
第2引数にはビューに渡す値を連想配列で指定
```php
public function index()
{
    return view('hello.index', ['msg' => '']);
}
```

リクエストパラメータを使用する例
$requestの中にフォーム送信された値や、クエリストリングの値が入っている
```php
public function index(Request $request)
{
    return view('hello.index', ['msg' => $request->msg]):
}
```

Request（Illuminate\Http\Request）の中身の例
```
GET /hello/index2 HTTP/1.1
Accept:                    text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3
Accept-Encoding:           gzip, deflate
Accept-Language:           ja,en-US;q=0.9,en;q=0.8
Cache-Control:             max-age=0
Connection:                keep-alive
Content-Length:
Content-Type:
Cookie:                    pma_lang=ja; XSRF-TOKEN=eyJpdiI6ImlwOTNYNUY4VDlUY0xEcWtTWjZNTHc9PSIsInZhbHVlIjoiXC8zS1dxa2NtYkJzaWx1NHhaRmxRV1VcL0YxbUhJVlwvazFnTlwvWWVnSkNHT3JrSndPN2tkcXNYR2NDMWpud3R4QXQiLCJtYWMiOiI1NGY5ZWYzYmNjYzIxNGY3M2FlZDNhMWM3ZDI1ZWIzZDg4NTY4MmY0NmM5MTM3ZDc1MzA3NDBkOTkyM2EzOTlmIn0%3D; laravel_session=eyJpdiI6Im1hMVZ6Tmc0OWJLM2lyUjVERGVEMlE9PSIsInZhbHVlIjoiT3hiZ1JZZG1wWkc5VWh4blJpWCtpTldCSVpobWxZS05XN2F1V1YycWUxSDNpMHFMaDZPdktVeGRpTEdaaUVGWCIsIm1hYyI6ImJmYWE3ZWMyOTA5MzRhMjYwMzc0ZTIwM2JhN2E0NTFlYmFmMDkxNmNmN2VhYTY4ZDU0ODQyMzdkMWU3YTAxNDUifQ%3D%3D
Dnt:                       1
Host:                      192.168.99.100
Upgrade-Insecure-Requests: 1
User-Agent:                Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36
Cookie: pma_lang=; XSRF-TOKEN=YkK2Irdp8qOuqBXG1un87OyAfO87Tcr1kiOk4jqB; laravel_session=06EA1t3oA2IN6lya1o5dd28jBCjcoYp1r7qjCI9L
```

Response（Illuminate\Http\Response）の中身の例
```
HTTP/1.0 200 OK
Cache-Control: no-cache, private
Date:          Mon, 06 May 2019 05:57:04 GMT
```

フォームリクエストを利用する例
以下のHelloRequestがフォームリクエストのクラス。  
フォーム送信された値のバリデーションチェックを行いたい場合などに用いる。
```php
public function post(HelloRequest $request)
{
    return view('hello.index', ['msg' => '正しく入力されています'])
}
```

### ヘルパ
#### redirect
引数ありの場合、RedirectResponseを返すもの
```php
       if ($validator->fails()) {
            // withErrors エラー情報リダイレクト先まで引き継ぐ
            // withInput　入力された情報リダイレクト先まで引き継ぐ
            return redirect('/validate')
                            ->withErrors($validator)
                            ->withInput();
        }
```
- withInput()　入力データを付加する
- withErrors(MessageProvider)　バリデータのエラーを付加する
- withCookie(クッキー配列）　クッキーを付加する

引数なしの場合はIlluminate\routingのRedirectorインスタンスを返す
- route（ルート名, 配列）　ルートの設定情報を指定（web.phpに記述したもの）
- action（アクションの指定, 配列）　アクション指定（HelloController@indexのように）
- view(ビュー名)　ビューを指定してリダイレクトする
- json(テキスト)　JSONデータを返す
- download(ファイルパス)　ファイルをダウンロード
- file(ファイルパス)

### シングルアクションコントローラ
一つのアクションしか持たないコントローラ  
__invokeアクションのみ持つ（ルート定義時にコントローラ名のみで指定可能）