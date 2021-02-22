## APIルート
Next.js では API ルートをサポート。  
Node.js サーバレス関数（ラムダとも）として、API エンドポイントを簡単に作成できる。

pages/api ディレクトリ配下に次の形式の関数を作成
```js
// req = HTTP incoming message, res = HTTP server response
export default function handler(req, res) {
  // ...
}
```

例
```js
export default function handler(req, res) {
  res.status(200).json({ text: 'Hello' })
}
```

req は、http.IncomingMessage のインスタンス + いくつかのビルド済みミドルウェア（[API Middlewares](https://nextjs.org/docs/api-routes/api-middlewares)）  
res は、http.ServerResponse のインスタンス + いくつかのヘルパー関数（[Response Helpers](https://nextjs.org/docs/api-routes/response-helpers)）

良い使い方としては、フォーム入力の処理。  
ページにフォームを作成して、API ルートに POST リクエストを送信という風にできる。  
API ルートコードはクライアントバンドルの一部ではないため、サーバー側のコードを安全に記述可能。コードを記述してデータベースに直接保存という風にもできる。

```js
export default function handler(req, res) {
  const email = req.body.email
  // Then save email to your database, etc...
}
```


### 注意点
#### getStaticProps または getStaticPathsから API ルートをフェッチしない
その代わりに、サーバー側のコードを getStaticProps またはgetStaticPaths に直接記述する（またはヘルパー関数を呼び出す）

getStaticProps と getStaticPaths はサーバー側でのみ実行され、クライアント側で実行されることはないため。  
ブラウザの JS バンドルには含まれないので、ブラウザに送信せずに、直接データベースクエリなどのコードを記述できる。

### 動的ルーティング
通常のページと同様に、API ルートも動的にすることができる。