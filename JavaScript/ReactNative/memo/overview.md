## React Native
Reactをベースとした、iOS/Androidアプリ開発のプラットフォーム。

### ディレクトリ構成
`expo init`した直後

```
ReactNative（大元のプロジェクトフォルダ）
├─ app（Expoプロジェクトフォルダ）
　　├─ .expo
　　├─ .expo-shared
　　├─ assets
　　├─ node_moodules
　　├─ .gitignore
　　├─ .watchmanconfig
　　├─ App.js
　　├─ app.json
　　├─ babel.config.js
　　├─ package.json
　　├─ yarn.lock
```

ソースディレクトリの例
```
ReactNative（大元のプロジェクトフォルダ）
├─ src
　　├─ components
　　├─ elements
　　├─ screens
```

- elements  
  最小単位

- components
  elementsを使用して構成したコンポーネント

- screens
  1ページ分