# Next.js

## 特徴
React アプリケーションを作るうえでは、以下のような点に注意を払う必要がある。
- コードは、webpackのようなバンドラーを使用してバンドルし、Babelのようなコンパイラーを使用して変換する必要がある
- コード分​​割などの本番最適化を行う必要がある。
- パフォーマンスとSEOのために、いくつかのページを静的に事前レンダリングすることを推奨。サーバー側のレンダリングまたはクライアント側のレンダリングを使用することもできる
- Reactアプリをデータストアに接続するには、サーバー側のコードを作成する必要がある場合がある

Next.js ではこれらの問題に対する解決策となる機能を、組み込みで持ち合わせている。
- 直感的なページベースのルーティングシステム（動的ルートをサポート）
- 事前レンダリング、静的生成（SSG）とサーバー側レンダリング（SSR）の両方がページごとにサポート
- ページの読み込みを高速化するための自動コード分割
- 最適化されたプリフェッチを使用したクライアント側ルーティング
- 組み込みのCSSとSassのサポート、および任意のCSS-in-JSライブラリのサポート
- 高速更新をサポートする開発環境
- サーバーレス関数を使用してAPIエンドポイントを構築するためのAPIルート
- 完全に拡張可能

## セットアップ
前提：Node.js 10.13以降

テンプレートを使用する例
```
$ npx create-next-app nextjs-blog --use-npm --example "https://github.com/vercel/next-learn-starter/tree/master/learn-starter"
```

サーバ立ち上げ
```
$ npm run dev
or
$ yarn dev
```

ブラウザからアクセス
```
localhost:3000
```

Next.js の開発サーバでは高速更新が有効になっている。
そのため、ファイルを変更すると即時にブラウザに自動反映する。

### TypeScript 化対応
tsconfig.json 作成
```
$ touch tsconfig.json
```
（この後にサーバを立ち上げなおすと、サーバが起動せず TypeScript のライブラリをインストールするように案内が表示されるようになっている）

必要なライブラリインストール
```
$ yarn add -D typescript @types/react @types/node
```

サーバ立ち上げ
```
$ npm run dev
or
$ yarn dev
```
こうすると Next.js は以下のことを行う
- tsconfig.json ファイルにデータを入力
- next-env.d.ts ファイルを作成。これにより、Next.js タイプがTypeScript コンパイラによって確実に取得されるようになる。このファイルには触れないこと。

---
データ取得関数は以下のような型を使う
```tsx
import { GetStaticProps, GetStaticPaths, GetServerSideProps } from 'next'

export const getStaticProps: GetStaticProps = async context => {
  // ...
}

export const getStaticPaths: GetStaticPaths = async () => {
  // ...
}

export const getServerSideProps: GetServerSideProps = async context => {
  // ...
}
```

API ルートは以下のような型を使う
```ts
import { NextApiRequest, NextApiResponse } from 'next'

export default (req: NextApiRequest, res: NextApiResponse) => {
  // ...
}
```

起点となる pages/_app.js は pages/_app.tsx とする
```tsx
import { AppProps } from 'next/app'

function App({ Component, pageProps }: AppProps) {
  return <Component {...pageProps} />
}

export default App
```

他ファイルも、.tsx（UI コンポーネント）、.ts（ロジック）へ拡張子を変更して、コードを最適化する。
