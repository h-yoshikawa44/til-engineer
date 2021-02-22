## データ取得

### getStaticProps
Next.js では、ページコンポーネントをエクスポートするとともに、getStaticProps という非同期関数をエクスポートすることもできる。  
この関数は、本番ビルド時に実行され、外部データを取得してページコンポーネントに渡す。  
（ページコンポーネント以外からはエクスポートできない）


```js
export default function Home(props) { ... }

export async function getStaticProps() {
  // Get external data from the file system, API, DB, etc.
  const data = ...

  // The value of the `props` key will be
  //  passed to the `Home` component
  return {
    props: ...
  }
}
```

この getStaticProps を使用すると、Next.js に次のように伝えられる  
「このページにはデータの依存関係がいくつかある。ビルド時にこのページを事前にレンダリングするときは、必ず最初に解決すること。」

**※開発モードにおいては、代わりに getStaticProps が各リクエストで実行される。**

注意点として、ビルド時に実行することを目的としているため、**クエリパラメータやHTTPヘッダーなど、リクエスト時にのみ利用可能なデータを使用することはできない。**

データ取得用のユーティリティ的な関数を lib ディレクトリ配下などに作っておき、それをインポートして使うなどするとよい。  
データ取得は、ファイルシステムを使って内部ファイルから取得もできるし、fetch 関数などをつかって外部 API から取得もできる。  
（Next.js は fetch 関数をクライアント側とサーバ側、両方でポリフィルするため、インポートする必要はない）

また、データベースに直接クエリを発行することも出来る。  
getStaticProps はサーバ側でのみ実行されるため、これが可能となっている。
```jsx
import someDatabaseSDK from 'someDatabaseSDK'

const databaseClient = someDatabaseSDK.createClient(...)

export async function getSortedPostsData() {
  // Instead of the file system,
  // fetch post data from a database
  return databaseClient.query('SELECT posts...')
}
```

### getServerSideProps
ビルド時ではなくリクエスト時にデータを取得する必要がある場合はこちら。  
getServerSideProps はリクエスト時に呼び出されるため、そのパラメーター（コンテキスト）にはリクエスト固有のパラメーターが含まれる。
```jsx
export async function getServerSideProps(context) {
  return {
    props: {
      // props for your component
    }
  }
}
```

サーバーはすべてのリクエストで結果を計算する必要があり、追加の構成なしでは結果を CDN でキャッシュできないため、最初のバイトまでの時間（TTFB）は getStaticProps よりも遅くなる。

リクエスト時にデータを取得する必要があるページを、事前にレンダリングする必要がある場合にのみ使用する。

### SWR
Next.js のチームが作成した SWR というデータ取得用の React Hooks ライブラリ。  
キャッシュ、再検証、フォーカストラッキング、間隔での再取得などを処理する。

クライアント側でデータ取得する場合に推奨。

```jsx
import useSWR from 'swr'

function Profile() {
  const { data, error } = useSWR('/api/user', fetch)

  if (error) return <div>failed to load</div>
  if (!data) return <div>loading...</div>
  return <div>hello {data.name}!</div>
}
```


### マークダウンファイルの YAML Front Matter
以下のように、上部についているメタデータセクションのこと。
```md
---
title: 'Two Forms of Pre-rendering'
date: '2020-01-01'
---

Next.js has two forms of pre-rendering: **Static Generation** and **Server-side Rendering**. The difference is in **when** it generates the HTML for a page.

- **Static Generation** is the pre-rendering method that generates the HTML at **build time**. The pre-rendered HTML is then _reused_ on each request.
- **Server-side Rendering** is the pre-rendering method that generates the HTML on **each request**.

Importantly, Next.js lets you **choose** which pre-rendering form to use for each page. You can create a "hybrid" Next.js app by using Static Generation for most pages and using Server-side Rendering for others.
```

gray-matter というライブラリを別途インストールして解析できる。
```
$ yarn add gray-matter
```
