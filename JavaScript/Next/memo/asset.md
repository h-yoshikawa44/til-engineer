## アセットの取り扱い
Next.js ではルート階層の public ディレクトリ配下で、画像をはじめとした静的アセットを提供できる。  
この public ディレクトリ配下のファイルは、ページと同様にアプリケーションのルートから参照できる。

### 画像ファイル
public/images 配下に設置。

通常は以下のような画像の使い方をする
```jsx
<img src="/images/profile.jpg" alt="Your Name" />
```
このやり方の課題として、以下のような点に注意する必要がある
- さまざまな画面サイズで画像がレスポンシブであることを確認する
- サードパーティのツールまたはライブラリを使用して画像を最適化する
- ビューポートに入るときにのみ画像をロードする

Next.js においては、next/image のコンポーネント使用することで、これらに対応できる。  
この next/image コンポーネントは、img タグを拡張し、最新の Web 用に進化させたもの。

Next.js では、デフォルトで画像最適化もサポート。  
これにより、ブラウザがサポートしている場合に、WebP などの最新の形式で画像のサイズ変更、最適化、および提供が可能となる。  
ビューポートが小さいデバイスに大きな画像を送信する必要がなくなる。  
また、Next.js が将来の画像形式を自動的に採用し、それらの形式をサポートするブラウザーに提供できるようにする。

Next.js はビルド時にイメージを最適化する代わりに、ユーザの要求に応じてオンデマンドでイメージを最適化。  
SSG や静的コンテンツのみのサイトとは異なり、画像の数によって、ビルド時間が増加することがない。

画像はデフォルトで遅延読み込みされる。  
つまり、ビューポート外の画像に対してページ速度が低下することはなく、画像はビューポートにスクロールされると読み込まれる。  
画像は常に、Google が検索ランキングで使用するコア WebVital である累積レイアウトシフトを回避するようにレンダリングされる。

next/image コンポーネントの使い方
```jsx
import Image from 'next/image'

const YourComponent = () => (
  <Image
    src="/images/profile.jpg" // Route of the image file
    height={144} // Desired size with correct aspect ratio
    width={144} // Desired size with correct aspect ratio
    alt="Your Name"
  />
)
```

### meta 情報
通常は head タグを用いるが、Next.js においては next/head コンポーネントを使用する。

next/head コンポーネントの使い方
```jsx
import Head from 'next/head'
import Link from 'next/link'

export default function FirstPost() {
  return (
    <>
      <Head>
        <title>First Post</title>
      </Head>
      <h1>First Post</h1>
      <h2>
        <Link href="/">
          <a>Back to home</a>
        </Link>
      </h2>
    </>
  )
}
```

なお、html タグの情報をカスタマイズしたい場合は、pages/_document.js ファイルを作成して行える。

### スタイル
Next.js では CSS と Sass のサポートが組み込まれている。  
TailwindCSS などの一般的な CSS ライブラリの使用もサポート。

```jsx
<style jsx>{`
  …
`}</style>
```
この形式は styled-jsx という「CSS-in-JS」ライブラリを使用。  
React コンポーネント内で CSS を記述でき、CSS スタイルのスコープが設定されるため、他のコンポーネントは影響を受けない。

Next.js には styled-jsx のサポートが組み込まれているが、styled-components や emotion などの他の一般的な CSS-in-JS ライブラリを使用することもできる。

#### レイアウトコンポーネント
全てのページで共有されるコンポーネント。  
ルート階層に components ディレクトリを作り、その配下にファイルを作成。

```jsx
export default function Layout({ children }) {
  return <div>{children}</div>
}
```

このレイアウトコンポーネントを適用したいコンポーネント側で呼び出す（一番外側でラップするように）
```jsx
import Head from 'next/head'
import Link from 'next/link'
import Layout from '../../components/layout'

export default function FirstPost() {
  return (
    <Layout>
      <Head>
        <title>First Post</title>
      </Head>
      <h1>First Post</h1>
      <h2>
        <Link href="/">
          <a>Back to home</a>
        </Link>
      </h2>
    </Layout>
  )
}
```

#### CSS モジュール
Next.js は CSS modules もサポート。  
CSS モジュールを使用することで、React コンポーネントにスタイルをあてられる。

ファイル拡張子は`.module.css`。  
スタイルを充てたいコンポーネントの名前を一致させて作る。
- コンポーネント：layout.js
- CSS モジュール：layout.module.css

CSS モジュールのスタイルを使用するには、コンポーネント側で以下の手順を行う
- CSS ファイルをインポートし、styles などの名前を割り当てる
- styles.container のように、className に割り当てる

```jsx
import styles from './layout.module.css'

export default function Layout({ children }) {
  return <div className={styles.container}>{children}</div>
}
```

割り当てたクラスは、CSS モジュールにより、自動的に一意のクラス名が生成されるため、クラス名の衝突が起こる心配がない。

また、Next.js のコード分割機能は CSS モジュールでも機能する。  
ページごとに最小限の CSS がロードされるようになっており、バンドルサイズをおさえられる。

CSS モジュールはビルド時に JavaScript バンドルから抽出され、Next.js によって自動的にロードされる .css ファイルを生成する。

#### グローバルなスタイル定義
グローバルにスタイルをあてたいという場合は、ルート階層に styles
ディレクトリを作成して、その配下にファイルを作成。
```css
html,
body {
  padding: 0;
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu,
    Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
  line-height: 1.6;
  font-size: 18px;
}

* {
  box-sizing: border-box;
}

a {
  color: #0070f3;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

img {
  max-width: 100%;
  display: block;
}
```

次に pages/_app.js を作成して、グローバル定義した CSS ファイルを読み込むようにする（なお、pages/_app.js 以外でグローバル定義の CSS ファイルをインポートすることはできない）
```jsx
import '../styles/global.css'

export default function App({ Component, pageProps }) {
  return <Component {...pageProps} />
}
```
この pages/_app.js は全てのページに共通するトップレベルのコンポーネントとなる。
**このコンポーネントを追加した場合は、サーバを再起動すること。**

#### classnames ライブラリを使った、クラスの切り替え
ライブラリインストール
```
$ yarn add classnames
```

例
alert.module.css
```css
.success {
  color: green;
}
.error {
  color: red;
}
```

```jsx
import styles from './alert.module.css'
import cn from 'classnames'

export default function Alert({ children, type }) {
  return (
    <div
      className={cn({
        [styles.success]: type === 'success',
        [styles.error]: type === 'error'
      })}
    >
      {children}
    </div>
  )
}
```

#### Tailwind CSS の導入方法
ライブラリインストール
```
$ yarn add tailwindcss postcss-preset-env postcss-flexbugs-fixes
```
※後者二つについては、Next.js のデフォルト動作に一致するようにさせるために使用する。

postcss.config.js を作成
```js
module.exports = {
  plugins: [
    'tailwindcss',
    'postcss-flexbugs-fixes',
    [
      'postcss-preset-env',
      {
        autoprefixer: {
          flexbox: 'no-2009'
        },
        stage: 3,
        features: {
          'custom-properties': false
        }
      }
    ]
  ]
}
```

tailwind.config.js でパージオプションを指定して、未使用の CSS を削除することを推奨。
```js
// tailwind.config.js
module.exports = {
  purge: [
    // Use *.tsx if using TypeScript
    './pages/**/*.js',
    './components/**/*.js'
  ]
  // ...
}
```