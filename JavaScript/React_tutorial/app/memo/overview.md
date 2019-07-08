### 教材
- [公式 チュートリアル](https://ja.reactjs.org/tutorial/tutorial.html)

### 開発支援
- [React Developer Tools](https://chrome.google.com/webstore/detail/react-developer-tools/fmkadmapgofadopljbjfkapdkoienihi/related?hl=ja)

### 概要
index.jsがデフォルトで読み込まれる

### コンポーネント
UIをコンポーネントと呼ばれる部品から組み立てることができる  
コンポーネントはReactに何を描画したいかを伝える  
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
コンポーネントのコンストラクタに`this.state`を設定することで、状態を持つことができるようになる  

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