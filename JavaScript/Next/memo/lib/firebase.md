## Next.js で Firebase 導入
基本的には、React アプリに導入する時と同じ。

### Firebase プロジェクト、アプリ作成
Firebase のコンソールで、プロジェクトを作成。  
その後、「ウェブ」のアプリを追加。

Google Analytics の導入は任意。

### Firebase SDK 設定（クライアントサイドで使用する場合）
※SDK バージョン 9系の手順  
　8系と9系とでは、大きく異なる点があることに注意
#### ライブラリインストール
```
$ yarn add firebase
```

#### SDK の初期化処理
lib/firebase.ts 作成

v9
```ts
import { initializeApp, getApps } from 'firebase/app'
import { getAnalytics } from 'firebase/analytics'

import 'firebase/analytics'
import 'firebase/auth'
import 'firebase/firestore'

if (typeof window !== 'undefined' && getApps().length === 0) {
  const firebaseConfig = {
    apiKey: process.env.NEXT_PUBLIC_FIREBASE_API_KEY,
    authDomain: process.env.NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
    databaseURL: process.env.NEXT_PUBLIC_FIREBASE_DATABASE_URL,
    projectId: process.env.NEXT_PUBLIC_FIREBASE_PROJECT_ID,
    storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.NEXT_PUBLIC_FIREBASE_APP_ID,
    measurementId: process.env.NEXT_PUBLIC_FIREBASE_MEASUREMENT_ID,
  }

  initializeApp(firebaseConfig)
  getAnalytics()
}
```

v8
```ts
import firebase from 'firebase/app'
import 'firebase/analytics'
import 'firebase/auth'
import 'firebase/firestore'

if (typeof window !== 'undefined' && firebase.apps.length === 0) {
  const firebaseConfig = {
    apiKey: process.env.NEXT_PUBLIC_FIREBASE_API_KEY,
    authDomain: process.env.NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
    databaseURL: process.env.NEXT_PUBLIC_FIREBASE_DATABASE_URL,
    projectId: process.env.NEXT_PUBLIC_FIREBASE_PROJECT_ID,
    storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.NEXT_PUBLIC_FIREBASE_APP_ID,
    measurementId: process.env.NEXT_PUBLIC_FIREBASE_MEASUREMENT_ID,
  }

  firebase.initializeApp(firebaseConfig)
  firebase.analytics()
}
```
※Firebase JavaScript SDK バーション7以前は`import * as firebase from 'firebase/app'`というインポートの仕方だった。

Firebase はブラウザ上で Next.js を動作させているときに利用するが、Next.js の仕様上、サーバサイドでも実行される。  
そのため、`window`オブジェクトがあるかどうかで分岐をさせている。
（`process.browser`でも判断可能）

また、この初期化ファイルが何度もインポートされ、そのたびに初期化されるのを防ぐために`getApps().length === 0`で、すでに初期化されている場合は何もしないようにしている。

#### .env.local を作成
自分のプロジェクトに合わせて適宜値を設定。
```
NEXT_PUBLIC_FIREBASE_API_KEY=XXXXXXXXXXXXXXXXXXXXXX
NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN=nextjs-question-box.firebaseapp.com
NEXT_PUBLIC_FIREBASE_DATABASE_URL=https://nextjs-question-box.firebaseio.com
NEXT_PUBLIC_FIREBASE_PROJECT_ID=nextjs-question-box
NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET=nextjs-question-box.appspot.com
NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID=00000000000000
NEXT_PUBLIC_FIREBASE_APP_ID=1:XXXXXXXXXXXXXXXXXXXXXXXXXXX
NEXT_PUBLIC_FIREBASE_MEASUREMENT_ID=G-XXXXXXXXXXXXXXXXXXXX
```
create-next-app で作成したアプリは、すでに dotenv が導入されているため、この形式のファイルが使える。  
`NEXT_PUBLIC_`というプレフィックスがついている環境変数は、自動的にブラウザ実行時にも利用できるようになっている（サーバサイドだけで利用する環境変数にこのプレフィックスをつけないこと）

#### SDK 設定を読み込む
pages/_app.tsx に以下を追記
```tsx
import '../lib/firebase'
```

あとは使いたい場所で各種インポートして使用する。

### Firebase Admin SDK 設定（サーバサイドで使用する場合）
基本的な使い方は、通常の SDK と同じ。  
こちらの場合は、セキュリティ保護ルールに関係なくデータ操作を行うことができる。

API で使用したい場合など。

#### ライブラリインストール
```
$ yarn add firebase-admin
```

#### 秘密鍵の取得と準備
コンソールの「プロジェクトを設定」「サービスアカウント」へ。  
Firebase Admin SDK で言語を選択し「新しい秘密鍵の生成」を選択。

この秘密鍵は一度発行すると、同じものを再生成はできない（鍵を無効化して、新しい鍵を発行はできる）  
また、秘匿情報であるため取り扱いには厳重注意すること。

この json ファイルを直接読み込む代わりに、環境変数に base64 エンコードした json ファイルの内容を指定し、実行時にデコードするとよい。

エンコード
```
// Windows
$ certutil -f -encode your.json encoded.txt

// Windows 以外
$ base64 -i your.json > encoded.txt
```

#### .env.local に情報追記
```
API_URL=http://localhost:3000
GCP_CREDENTIAL=base64エンコードされた文字列
```
**`NEXT_PUBLIC_`のプレフィックスは絶対につけないこと。**

base64エンコードされた文字列は、上記の手順でエンコードした文字列を次のように加工したもの
- 改行を`\n`に置き換え1行に
- `-----BEGIN CERTIFICATE-----` と `-----END CERTIFICATE-----`を取り除いたもの（Windows の場合）


#### Admin SDK の初期化処理
lib/firebase_admin.ts
```ts
import * as admin from 'firebase-admin'

if (admin.apps.length == 0) {
  const credential = JSON.parse(
    Buffer.from(
      process.env.GCP_CREDENTIAL.replace(/\\n/g, '\n'),
      'base64'
    ).toString()
  )

  admin.initializeApp({
    databaseURL: process.env.NEXT_PUBLIC_FIREBASE_DATABASE_URL,
    credential: admin.credential.cert(credential),
  })
}
```

あとは使いたい場所で各種インポートして使用する。

### Firebase Auth の使い方
v9
```ts
import { getAuth, onAuthStateChanged, signInAnonymously } from 'firebase/auth'
.
.
.
function useAuthentication() {
  const [user, setUser] = useRecoilState(userState)

  useEffect(() => {
    if (user !== null) {
      return
    }
    const auth = getAuth()

    signInAnonymously(auth).catch(function (error) {
      // Handle Errors here.
      console.error(error)
      // ...
    })

    onAuthStateChanged(auth, function (firebaseUser) {
      if (firebaseUser) {
        const loginUser: User = {
          uid: firebaseUser.uid,
          isAnonymous: firebaseUser.isAnonymous,
          name: ''
        }
        setUser(loginUser)
        createUserIfNotFound(loginUser)
      } else {
        // User is signed out.
        setUser(null)
      }
      // ...
    })
  }, [])

  return { user }
}
```

### Firestore の使い方
Firebase コンソールからデータベースを作成。

Firestore では以下の概念がある
- コレクション：データを入れる箱（RDB でいうテーブル）
- ドキュメント：コレクションの中のデータ（RDB でいうレコード）


SDK 初期化時に Firestore も含める
```ts
import 'firebase/firestore'
```

#### 使い方の例
v9
```ts
import {
  getFirestore,
  collection,
  doc,
  getDoc,
  setDoc,
} from 'firebase/firestore'
.
.
.
  const db = getFirestore()
  const usersCollection = collection(db, 'users')
  const userRef = doc(usersCollection, user.uid)
  const document = await getDoc(userRef)
  if (document.exists()) {
    // すでに存在する場合は書き込まない
    return
  }

  await setDoc(userRef, {
    name: 'taro' + new Date().getTime(),
  })
```

v8
```ts
import firebase from 'firebase/app'
.
.
.
  const userRef = firebase.firestore().collection('users').doc(user.uid)
  const doc = await userRef.get()
  if (doc.exists) {
    // すでに存在する場合は書き込まない
    return
  }

  await userRef.set({
    name: 'taro' + new Date().getTime(),
  })
```
doc メソッドを使用すると、ドキュメントの ID を指定できる。  
また、collection のあとに where で条件を指定したり、limit で取得数の指定をしたりもできる(v8)

複数の更新処理をしたい場合は、トランザクションを使用するとよい。

v9
```ts
import {
  collection,
  doc,
  getDoc,
  getDocs,
  getFirestore,
  limit,
  query,
  runTransaction,
  serverTimestamp,
  Timestamp,
  where,
} from 'firebase/firestore'
.
.
.
  await runTransaction(db, async (t) => {
    t.set(answerRef, {
      uid: user.uid,
      questionId: question.id,
      body,
      createdAt: serverTimestamp(),
    })
    t.update(doc(questionsCollection, question.id), {
      isReplied: true,
    })
  })
```

v8
```ts
  await firebase.firestore().runTransaction(async (t) => {
    t.set(answerRef, {
      uid: user.uid,
      questionId: question.id,
      body,
      createdAt: firebase.firestore.FieldValue.serverTimestamp(),
    })
    t.update(firebase.firestore().collection('questions').doc(question.id), {
      isReplied: true,
    })
  })
```

#### セキュリティ保護ルール
Firebase は設定情報を誰でも見ることができる関係で、誰でも比較的簡単にデータを閲覧できてしまう。  
そのため、適切にセキュリティ保護ルールを設定しておく必要がある。

コンソールからルールタブを開く
```js
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{uid} {
      allow update, delete: if request.auth.uid == uid;
      allow create, read: if request.auth.uid != null;
    }
    match /questions/{questionId} {
      allow create: if request.auth.uid != null &&
        request.auth.uid != request.resource.data.receiverUid;
      allow read: if
        request.auth.uid == resource.data.senderUid ||
        request.auth.uid == resource.data.receiverUid;
      allow update: if request.auth.uid == resource.data.receiverUid;
      allow delete: if false;
    }
    match /answers/{answerId} {
  		allow update, delete: if request.auth.uid == resource.data.uid;
  		allow create, read: if request.auth.uid != null;
		}
  }
}
```
allow の後につくオペレーション
- read → get, list
- write → create, update, delete
if で受け付ける条件を定義する。

request.auth.uid は現在ログインしているユーザの uid  
resource.data は現在データベースにあるデータの値  
request.resource.data がその時送信されているデータの値

このルールはファイル管理して、Firebase CLI でデプロイすることも可能。
