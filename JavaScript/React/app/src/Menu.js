import React from "react";
import {BrowserRouter as Router, Link, Route, Switch} from "react-router-dom";
import TicTacToe from './Tic-tac-toe/Tic-tac-toe';
import LatitubeLongitubeSearch from './LatitubeLongitubeSearch/LatitubeLongitubeSearch';

const topPage = () => <div><h1>Top Page</h1>ここがトップページです</div>
const page3 = () => <div><h1>page3</h1>3枚目のページです</div>
const page404 = () => <div><h1>404</h1>存在しないページです</div>  //<= ヒットしなかった時用のページを追加

const Menu = (props) => {
  const liStyle = {
    display: 'inline',
    width: '100px'
  }

  return (
    <Router>
      <div style={{width: '500px', textAlign: 'left'}}>
        <ul style={{display: 'flex'}}>
          <li style={liStyle}><Link to='/'>top</Link></li>
          <li style={liStyle}><Link to='/TicTacToe'>三目並べ</Link></li>
          <li style={liStyle}><Link to='/LatitubeLongitubeSearch'>緯度経度検索</Link></li>
          <li style={liStyle}><Link to='/page3'>page3</Link></li>
        </ul>

        <div style={{marginLeft: '50px'}}>
          <Switch>
            <Route path='/' exact component={topPage}/>
            <Route path='/TicTacToe' exact component={TicTacToe}/>
            <Route path='/LatitubeLongitubeSearch' exact render={() => <LatitubeLongitubeSearch place={props.place}/>}/>
            <Route path='/page3' exact component={page3}/>
            {/* 一番末尾に追加 pathの指定も、対応するLinkの追加も必要ない */}
            <Route exact component={page404}/>
          </Switch>
        </div>
      </div>
    </Router>
  )
}

export default Menu