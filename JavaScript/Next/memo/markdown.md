## マークダウン
※routing.md の動的ルーティングの内容の派生

### マークダウンのレンダリングまでやりたい時
ライブラリインストール
```
$ yarn add remark remark-html
```

post データ取得関数の中でマークダウンの変換処理をいれて、それも含めて返すようにする。  
`await remark()`が必要になるため、関数自体を async で非同期化。
```js
import remark from 'remark'
import html from 'remark-html'
.
.
.
export async function getPostData(id) {
  const fullPath = path.join(postsDirectory, `${id}.md`)
  const fileContents = fs.readFileSync(fullPath, 'utf8')

  // Use gray-matter to parse the post metadata section
  const matterResult = matter(fileContents)

  // Use remark to convert markdown into HTML string
  const processedContent = await remark()
    .use(html)
    .process(matterResult.content)
  const contentHtml = processedContent.toString()

  // Combine the data with the id and contentHtml
  return {
    id,
    contentHtml,
    ...matterResult.data
  }
}
```

この関数を呼び出す getStaticProps も async/await をつけておく
```js
export async function getStaticProps({ params }) {
  const postData = await getPostData(params.id)
  return {
    props: {
      postData
    }
  }
}
```

ページコンポーネント側で、マークダウンを変換したものを使うようにする
```jsx
export default function Post({ postData }) {
  return (
    <Layout>
      {postData.title}
      <br />
      {postData.id}
      <br />
      {postData.date}
      <br />
      <div dangerouslySetInnerHTML={{ __html: postData.contentHtml }} />
    </Layout>
  )
}
```

### post ページにメタ情報をいれる
[id].js に next/head + 各 post データの値 でいれればいい
```jsx
import Head from 'next/head'

export default function Post({ postData }) {
  return (
    <Layout>
      <Head>
        <title>{postData.title}</title>
      </Head>

      {/* Keep the existing code here */}
    </Layout>
  )
}
```

### 日付のフォーマット
ライブラリインストール
```
$ yarn add date-fns
```

例
components/date.js で Date コンポーネントを作成
```js
import { parseISO, format } from 'date-fns'

export default function Date({ dateString }) {
  const date = parseISO(dateString)
  return <time dateTime={dateString}>{format(date, 'LLLL d, yyyy')}</time>
}
```

[id].js で作成した Date コンポーネントを使う
```jsx
import Date from '../../components/date'

export default function Post({ postData }) {
  return (
    <Layout>
      {/* Keep the existing code here */}

      {/* Replace {postData.date} with this */}
      <Date dateString={postData.date} />

      {/* Keep the existing code here */}
    </Layout>
  )
}
```

### スタイルをあてたい
[id].js で CSS モジュールファイルを読み込んで、クラスを割り当てるなど。

```jsx
import Head from 'next/head'
import Layout from '../../components/layout'
import utilStyles from '../../styles/utils.module.css'
.
.
.
export default function Post({ postData }) {
  return (
    <Layout>
      <Head>
        <title>{postData.title}</title>
      </Head>
      <article>
        <h1 className={utilStyles.headingXl}>{postData.title}</h1>
        <div className={utilStyles.lightText}>
          <Date dateString={postData.date} />
        </div>
        <div dangerouslySetInnerHTML={{ __html: postData.contentHtml }} />
      </article>
    </Layout>
  )
}
```

### 記事一覧で、各種記事へのリンクを作りたい
例
```jsx
import Head from 'next/head'
import Link from 'next/link'
import Layout, { siteTitle } from '../components/layout'
import Date from '../components/date'
import utilStyles from '../styles/utils.module.css'
import { getSortedPostsData } from '../lib/posts'

export default function Home({ allPostsData }) {
  return (
    <Layout home>
      <Head>
        <title>{siteTitle}</title>
      </Head>
      <section className={`${utilStyles.headingMd} ${utilStyles.padding1px}`}>
        <h2 className={utilStyles.headingLg}>Blog</h2>
        <ul className={utilStyles.list}>
          {allPostsData.map(({ id, date, title }) => (
            <li className={utilStyles.listItem} key={id}>
            <Link href={`/posts/${id}`}>
              <a>{title}</a>
            </Link>
            <br />
            <small className={utilStyles.lightText}>
              <Date dateString={date} />
            </small>
          </li>
          ))}
        </ul>
      </section>
    </Layout>
  )
}
```