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
```

ブラウザからアクセス
```
localhost:3000
```

Next.js の開発サーバでは高速更新が有効になっている。
そのため、ファイルを変更すると即時にブラウザに自動反映する。