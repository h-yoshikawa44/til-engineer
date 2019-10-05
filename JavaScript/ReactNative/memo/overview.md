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

### タグ
通常のReactとは違い、専用のタグを用いて画面を構築していく。

- View  
HTMLでいうDivタグ。  
このタグにはcolor、fontSizeは設定することができない。この場合はViewの中になるTextに設定するようにする。

- Text
HTMLでいうh1などの見出しやpタグ

### style
基本的にはCSSで書く場合と同じであるが、キャメルケースで書くことと、一部CSSと違う名称の設定値があるので注意。


### propsの受け渡し
コンポーネントのタグで囲うようにして渡された要素には、コンポーネント側で`{this.props.children}`でアクセスできる

App.js側
```js
export default function App() {
  return (
    <View style={styles.container}>
      <Text>Open up App.js to start working on your app!</Text>
      <BodyText>Hi!</BodyText>
    </View>
  );
}
```

BodyText.js側
```js
class BodyText extends React.Component {
  render() {
    return (
      <View>
        <Text style={styles.text}>
          {this.props.children}
        </Text>
      </View>
    )
  }
}
```