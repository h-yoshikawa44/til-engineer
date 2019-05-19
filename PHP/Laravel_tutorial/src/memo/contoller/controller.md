## コントローラ

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
