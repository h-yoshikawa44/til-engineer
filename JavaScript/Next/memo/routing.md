## ルーティング
Next.js では、pages ディレクトリ配下のファイルからエクスポートされた React コンポーネントがページとなる。

### 基本的な仕組み
ページは、ファイル名に基づいてルートに関連付けられるようになっている。
- `pages/index.js` → /
- `pages/about.js` → /about
- `pages/about/index.js` → /about
- `pages/posts/first-post.js` → /posts/first-post

※注意点として、コンポーネントには任意の名前を付けることができるが、デフォルトのエクスポートとしてエクスポートする必要がある。

つまりは、pages ディレクトリ配下 JS ファイルを作成するだけで、ファイルへのパスが URL パスとなる。  
（別途、ルーティングライブラリを導入する必要がない）

### 特殊なページ
#### pages/_app.js（pages/_app.tsx）
アプリケーション初期化で、最初に一度だけ読み込まれるページ。
初期化処理やグローバル CSS の読み込みなどを行う
#### pages/_document.js（pages/_document.tsx）
画面の全体構造を定義したい時に使用。
### リンク
通常、リンクは a タグを使って実現するが、Next.js においては、a タグをラップした next/link のリンクコンポーネントを使用。 Link コンポーネントを使用することで、アプリケーションの別のページにクライアント側で移動できる。  
（クライアント側でのナビゲーション → JS を使用してのページ遷移であり、ブラウザの更新による遷移と比べて高速に動作する）

```jsx
import Link from 'next/link'
.
.
.
<h1 className="title">
  Read{' '}
  <Link href="/posts/first-post">
    <a>this page!</a>
  </Link>
</h1>
```

ただし、Next.js アプリ外へのリンクを作る場合は、Link コンポーネントでなく、a タグを使用する。

### 自動コード分割
Next.js ではコード分割を自動的に行われる。  
各ページはそのページに必要なものだけを読み込むようになっている。

そのため、数百ページ存在するというサイトでも高速に読み込まれる。  
また、特定のページがエラーになったとしても、他のアプリケーションの部分は引き続き機能する。

さらに Next.js の本番向けビルドでは、リンクコンポーネントがブラウザのビューポートに表示されるたびに、Next.js はリンクされたページのコードをバックグラウンドで自動的にプリフェッチする。  
つまりリンクをクリックする前に、すでにリンク先ページのコードはバックグラウンドで読み込まれているため、高速にページ遷移が行われる。

### 動的ルーティング
[XX].js というようなファイルやディレクトリを作ると動的なルーティングを実現できる。  
pages/users/[userId].js というファイルがあったとすると、/users/1 や /users/hoge などが該当する。

- pages/users/[userId].js → /users/hoge
- pages/[a]/[b].js → /hoge/fuga
- pages/[...ids].js → /a/b/c/d

内部外部問わず、どこかから複数のデータを取得してきて、それをもとに動的ルーティングにしたい場合は、getStaticPaths と getStaticProps を定義するとよい。  
getStaticProps で取得したデータを元にしたルートを生成するということが可能。

例
```
[
  { id: "foo", ... },
  { id: "bar", ... },
]
```
↓
/posts/foo
/posts/bar

[XX].js というファイルを作り、getStaticPaths と getStaticProps を定義することで、動的ルーティングの実装ができる。

getStaticPaths に関して
- 開発モード：全てのリクエストで実行される
- 本番モード：ビルド時に実行される

返り値に含まれる fallback について
- false にする
  - getStaticPaths によって返されないパスは404になる
- true にする
  - getStaticPaths から返されるパスは、ビルド時に HTML にレンダリングされる。
  - ビルド時に生成されていないパスは、404ページにはならない。代わりに、Next.js は、そういったパスへの最初のリクエストでページの「フォールバック」バージョンを提供。
  - バックグラウンドで、Next.js は要求されたパスを静的に生成。同じパスへの後続のリクエストは、ビルド時に事前にレンダリングされた他のページと同じように、生成されたページを提供。

#### 使い方の例
lib ディレクトリ配下などに、posts ディレクトリ内のファイル名（.md を除く）のリストを返す関数を作る。  
この時、返り値としてはリストの各オブジェクトに params キーがあり、その中に id キーがあるようにする。
```js
// postsディレクトリ内のファイル名（.mdを除く）のリストを返す
export function getAllPostIds() {
  const fileNames = fs.readdirSync(postsDirectory)

  // Returns an array that looks like this:
  // [
  //   {
  //     params: {
  //       id: 'ssg-ssr'
  //     }
  //   },
  //   {
  //     params: {
  //       id: 'pre-rendering'
  //     }
  //   }
  // ]
  return fileNames.map(fileName => {
    return {
      params: {
        id: fileName.replace(/\.md$/, '')
      }
    }
  })
}
```

同様に、指定された id を持つ post データを取得する関数を作る
```js
export function getPostData(id) {
  const fullPath = path.join(postsDirectory, `${id}.md`)
  const fileContents = fs.readFileSync(fullPath, 'utf8')

  // Use gray-matter to parse the post metadata section
  const matterResult = matter(fileContents)

  // Combine the data with the id
  return {
    id,
    ...matterResult.data
  }
}
```

pages/posts 配下に [id].js というページを作成し、getStaticPaths と getStaticProps を定義
```jsx
import Layout from '../../components/layout'
import { getAllPostIds, getPostData } from '../../lib/posts'

export default function Post({ postData }) {
  return (
    <Layout>
      {postData.title}
      <br />
      {postData.id}
      <br />
      {postData.date}
    </Layout>
  )
}

export async function getStaticPaths() {
  const paths = getAllPostIds()
  return {
    paths,
    fallback: false
  }
}

export async function getStaticProps({ params }) {
  const postData = getPostData(params.id)
  return {
    props: {
      postData
    }
  }
}

```
流れとしてはこんなイメージ
1. getStaticPaths で各 posts データの id を含むリストを取得（このリストをもとに動的 URL 生成）
2. 1で取得したリストの分、以下繰り返し
  1. getStaticProps で、id それぞれを引数として post データを取得
  2. 取得した post データを使って、レンダリング

#### 全てのリクエストキャッチする動的ルート
[...XX].js の形式のファイルを作成すると、全てのリクエストをキャッチするタイプになる。

例
pages/posts/[...id].js
- /posts/a
- /posts/a/b
- /posts/a/b/c

この場合の getStaticPaths の返り値として、以下のように id の配列を返す必要がある
```jsx
return [
  {
    params: {
      // Statically Generates /posts/a/b/c
      id: ['a', 'b', 'c']
    }
  }
  //...
]
```

getStaticProps に渡って来る params も配列に変わる
```jsx
export async function getStaticProps({ params }) {
  // params.id will be like ['a', 'b', 'c']
}
```

### Next.js のルータを使いたい
next/router から useRouter フックをインポートすることで使用できる

### カスタム 404 ページ
pages/404.js を作ることで対応可能。

```jsx
export default function Custom404() {
  return <h1>404 - Page Not Found</h1>
}
```
