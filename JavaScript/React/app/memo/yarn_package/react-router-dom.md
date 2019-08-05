## react-router-dom
画面遷移を実装できるライブラリ

最初だけサーバから情報を取得し、あとはコンポーネント部分を再描画するシングルページアプリケーションを実現できる  
通常のaタグで遷移するよりも、高速に動作する

参考：
- [公式ドキュメント](https://reacttraining.com/react-router/web/guides/quick-start)
- [react-router-dom v4 入門してみた](https://qiita.com/katatu801/items/81253b54b6b6713e1332)

### インストール
`yarn add react-router-dom`

### 実装

リンクの作成例（このMenu.jsをApp.jsから読み込むようにする）
```js
import React from "react";
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";

const topPage = () => <div><h1>Top Page</h1>ここがトップページです</div>
const page1 = () => <div><h1>page1</h1>1枚目のページです</div>
const page2 = () => <div><h1>page2</h1>2枚目のページです</div>
const page3 = () => <div><h1>page3</h1>3枚目のページです</div>
const page404 = () => <div><h1>404</h1>存在しないページです</div>  //<= ヒットしなかった時用のページを追加

const Menu = () => {
  const liStyle = {
    display: 'inline',
    width: '100px'
  }

  return (
    <Router> // 画面遷移時にヒストリーAPIに履歴情報を追加してくれるコンポーネント（BroeserRouterのエイリアスとして定義されることが多い）まずこのタグで囲う
      <div style={{width: '500px', textAlign: 'left'}}>
        <ul style={{display: 'flex'}}>
          <li style={liStyle}><Link to='/'>top</Link></li> // Link aタグと同様にリンクを作成
          <li style={liStyle}><Link to='/page1'>page1</Link></li>
          <li style={liStyle}><Link to='/page2'>page2</Link></li>
          <li style={liStyle}><Link to='/page3'>page3</Link></li>
        </ul>

        <div style={{marginLeft: '50px'}}>
          <Switch>　// 上から順に現在のpathにマッチするRouteを探して見つけた場合、それを描写する(exactがついているものはパスの完全一致で判断する)
            <Route path='/' exact component={topPage}/> // componentに他ファイルのエクスポートしたコンポーネントを指定もできる
            <Route path='/page1' exact component={page1}/>
            <Route path='/page2' exact component={page2}/>
            <Route path='/page3' exact component={page3}/>
            <Route exact component={page404}/> // 一番末尾に追加 pathの指定も、対応するLinkの追加も必要ないことで、どのURLにもマッチしない場合のページを描画
          </Switch>
        </div>
      </div>
    </Router>
  )
}

export default Menu
```