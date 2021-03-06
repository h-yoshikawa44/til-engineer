## Recoil
状態管理ライブラリ。  
Redux と比べシンプルな構造をしている。

### 導入手順
ライブラリインストール
```
$ yarn add recoil
```

pages/_app.tsx で初期設定
```tsx
import { RecoilRoot } from 'recoil'
import '../styles/globals.css'

function MyApp({ Component, pageProps }) {
  return (
    <RecoilRoot>
      <Component {...pageProps} />
    </RecoilRoot>
  )
}

export default MyApp
```
Recoil を利用するコンポーネントを`RecoilRoot`で囲む。

### 使い方の例
・データモデルを定義する。

models/User.ts
```ts
export interface User {
  uid: string
  isAnonymous: boolean
}
```

・データモデル情報をインポートして、データを格納する箱の定義。
hooks 配下（カスタムフック定義ファイル）など
```ts
import { User } from '../models/User'
import { atom } from 'recoil'

const userState = atom<User>({
  key: 'user',
  default: null,
})
```
Recoil では atom という関数により、データ格納する箱を定義する。  
key はその箱の名前。default はデフォルト値。  
データモデル情報をジェネリクスとして指定することで、後にこのステートを利用する際に入力補完が効いたりするメリットが得られる。

・`useRecoilState`で Recoil のステートに保持されているデータを利用
```ts
import { atom, useRecoilState } from 'recoil'

function useAuthentication() {
  const [user, setUser] = useRecoilState(userState)
  .
  .
  .
}
```
この場合、  
user はRecoil のステートに保持されているユーザーデータ。  
serUser はユーザ情報を更新できる関数。
