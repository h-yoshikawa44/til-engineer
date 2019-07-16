### 開発支援
- [React Developer Tools](https://chrome.google.com/webstore/detail/react-developer-tools/fmkadmapgofadopljbjfkapdkoienihi/related?hl=ja)

### 概要
index.jsから読み込まれ、最終的にindex.htmlに変換して描画する  

基本的なApp.js構成例
```js
import React from 'react';  // Reactをimport
class App extends React.Component {  // React.componentを継承するクラスの定義
  render() {  // JSXを戻り値とするrenderメソッドを定義
    return (
      <h1>Hello World</h1>
    );
  }
}

export default App;  // クラスをexport
```

Reactだけで構築されたアプリケーションは、通常ルートDOMノードを一つだけ持つ  
既存アプリにReactを統合しようとしている場合は、独立したDOMノードを好きなだけ持つことができる
React 要素をルート DOM ノードにレンダリングするには、その 2 つを ReactDOM.render() に渡す

単純なHTMLを描画する例
```js
ReactDOM.render(
  <h1>Hello, world!</h1>,
  document.getElementById('root')  // index.htmlのidがrootの箇所にレンダリング
);
```

コンポーネントを描画する例(index.js)
```js
ReactDOM.render(
  <Game />,
  document.getElementById('root')
);
```

### JSX
JavaScrirtとHTMLが合わさったような構文  
命名規則はキャメルケース  
imgタグのようなHTMLでは閉じタグが必要なかったものは、末尾に/をつける
例 <img src='画像URL' />

デフォルトでは、React DOM は JSX に埋め込まれた値をレンダリングされる前にエスケープするため、XSSなどインジェクション攻撃を防ぐことができる

```js
const name = 'Josh Perez';
const element = <h1>Hello, {name}</h1>;

ReactDOM.render(
  element,
  document.getElementById('root')
);
```

BabelはJSXをReact.createElement()の呼び出しにコンパイルする

JSXではreturn内に複数の要素があるとエラーになる
ダメな例
```js
render() {
  return (
    <h1>H1です</h1>
    <h2>h2です</h2>
    <p>pです</p>
  )
}
```
OKな例
```js
render() {
  return (
    <div>
      <h1>H1です</h1>
      <h2>h2です</h2>
      <p>pです</p>
    </div>
  );
}
```

JSXにJSを埋め込む時は{}で囲う
```js
render() {
  const text = 'Hello World';
  return (
    <div>{text}</div>
  );
}
```

クラス名を記述する際は`className`を使う
例 <h1 className='title'>Hello World</h1>


### コンポーネント
UIをコンポーネントと呼ばれる部品から組み立てることができる  
コンポーネントはReactに何を描画したいかを伝える(renderメソッド)  
データが変更されると、Reactはコンポーネントを効率よく更新して再レンダーする

React.Componentのサブクラス(JSX)
```js
class ShoppingList extends React.Component {
  render() {
    return (
      <div className="shopping-list">
        <h1>Shopping List for {this.props.name}</h1>
        <ul>
          <li>Instagram</li>
          <li>WhatsApp</li>
          <li>Oculus</li>
        </ul>
      </div>
    );
  }
}

// Example usage: <ShoppingList name="Mark" />
```
上記のコードは以下と同じである
```js
React.createElement("div", {
    className: "shopping-list"
  },
  React.createElement("h1", null, "Shopping List for ", props.name),
  React.createElement("ul", null,
    React.createElement("li", null, "Instagram"),
    React.createElement("li", null, "WhatsApp"),
    React.createElement("li", null, "Oculus")
  )
);
```

コンポーネント呼び出し側
```js
import ShoppingList from './ShoppingList;

class App extends React.Component {
  render() {
    return (
      <ShoppingList />
    )
  }
}
```
なお、同じコンポーネントを複数表示する場合は、mapメソッドを利用すると便利

### イベント
タグ内に`イベント名 = {() => {処理}}`で書ける
例　<button onClick={() => {console.log('Hello World')}}>こんにちは</button>

### props
propertiesの略  
プロパティの値にアクセスする  
propsを使うことで親コンポーネントが子コンポーネントに情報を渡すことができる
`this.props.value`の場合、そのコンポーネントのプロパティのvalueの値を意味する  
使い方はsrc/index.js参照

```js
class Square extends React.Component {
    render() {
      return (
        // クリックでアラートを出す(アロー関数を使って記述・onClickプロパティに渡しているのは関数である)
        <button className="square" onClick={()　=> { alert('click'); }}>
          {/* valueプロパティに渡された値 */}
          {this.props.value}
        </button>
      );
    }
  }

  class Board extends React.Component {
    renderSquare(i) {
      return <Square value={i}/>;
    }
```

### state
コンポーネントのコンストラクタにオブジェクトとして`this.state`を設定することで、状態を持つことができるようになる  

設定するには、まずクラスにコンストラクタを追加してstateを初期化する  
(JavaScript のクラスでは、サブクラスのコンストラクタを定義する際は常に super を呼ぶ必要がある)
```js
class Square extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: null,
        }
    }

    ※以下省略
  }
```

#### stateの値のセット  
setState をコンポーネント内で呼び出すと、React はその内部の子コンポーネントも自動的に更新する
```js
class Square extends React.Component {
    ※一部省略

    render() {
      return (
        // クリックでstateに値をセットする(アロー関数を使って記述・onClickプロパティに渡しているのは関数である)
        <button className="square" onClick={()　=> this.setState({value: 'X'})}>
          {/* stateの値が入る */}
          {this.state.value}
        </button>
      );
    }
  }
```

子コンポーネントから親コンポーネントのstateを変更したい場合  
`this.state`はそのコンポーネント内でプライベートになるため、直接変更することは不可

この場合は、親コンポーネントから子コンポーネントに関数を渡すようにして、この関数を子コンポーネントで呼び出すことで対処可能
```js
class Square extends React.Component {
    ※一部省略

    render() {
      return (
        // クリックで関数を通してBoardのstateに値をセットする(アロー関数を使って記述・onClickプロパティに渡しているのは関数である)
        <button className="square" onClick={()　=> this.props.onClick()}>
          {/* valueの値が入る */}
          {this.props.value}
        </button>
      );
    }
  }

class Board extends React.Component {
 　　※一部省略

    handleClick(i) {
        // 配列のコピーを作成
        const squares = this.state.squares.slice();
        squares[i] = 'X';
        this.setState({squares: squares});
    }

    renderSquare(i) {
      return <Square value={this.state.squares[i]} onClick={() => this.handleClick(i)}/>;
    }
```

### 関数コンポーネント
renderメソッドだけを持ち、自分のstateを持たないコンポーネントをシンプルに書くための方法  
React.Componentを継承するクラスを定義する代わりに、propsを入力として受け取り表示すべき内容を返す関数を定義する

```js
function Square(props) {
return (
    // クリックで関数を通してBoardのstateに値をセットする
    <button className="square" onClick={props.onClick}>
    {props.value}
    </button>
  );
}
```

### key
リストをレンダーする際、リストの項目それぞれについて、Reactはとある情報を保持する。  
リストが変更になった場合、Reactはどのアイテムが変更になったのかを知る必要がある。  

Reactはリストの項目それぞれに対してkeyプロパティを与えることで、兄弟要素の中でそのアイテムを区別できるようにする必要がある。  
```js
<li key={user.id}>{user.name}: {user.taskCount} tasks left</li>
```

リストが再レンダーされる際、Reactはそれぞれのリスト項目のkeyについて、前回のリスト項目内に同じkeyを持つものがないか探す。  
もし以前になかったkeyがリストに含まれていれば、Reactはコンポーネントを作成する。もし以前のリストのあったkeyが新しいリストに含まれていなければ、Reactは以前のコンポーネントを破棄する。もし二つのkeyがマッチした場合、対応するコンポーネントは移動される。  
keyはそれぞれのコンポーネントの同一性に関する情報をReactに与え、それによりReactは再レンダー間でstateを保持できるようになる。もしコンポーネントの key が変化していれば、コンポーネントは破棄されて新しい state で再作成される。

keyはpropsの一部のようにも見えるが、`this.props.key`で参照することはできない

**動的なリストを構築する場合は正しい key を割り当てることが強く推奨される**

keyはグローバルに一意である必要はなく、コンポーネントとその兄弟の間で一意であれば十分