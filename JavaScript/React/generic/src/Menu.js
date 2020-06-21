import React from "react";
import {BrowserRouter as Router, Link, Route, Switch } from "react-router-dom";
import TicTacToe from './Tic-tac-toe/Tic-tac-toe';
import ApiTutorial from './Api-tutorial/Api-tutorial';
import Othello from "./Othello/Othello";
import NavHOC from './HistoryNavHOC';
import NavHooks from './HistoryNavHooks';
import PropTypeRoute from './Library/PropTypes/PropTypesRoute';
import MaterialUI from './Library/MaterialUI/Route';
import Recompose from './Library/Recompose/Route';
import Konva from './Library/ReactKonva/Component';

const topPage = () => <div><h1>Top Page</h1>ここがトップページです</div>
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
          <li style={liStyle}><Link to='/ApiTutorial'>APIチュートリアル</Link></li>
          <li style={liStyle}><Link to='/Othello'>オセロ</Link></li>
        </ul>
        <ul style={{display: 'flex'}}>
          <li style={liStyle}><Link to='/PropTypes'>PropTypes</Link></li>
          <li style={liStyle}><Link to='/MaterialUI'>MaterialUI</Link></li>
          <li style={liStyle}><Link to='/Recompose'>Recompose</Link></li>
          <li style={liStyle}><Link to='/ReactKonva'>ReactKonva</Link></li>
        </ul>
        <div style={{margin: '20px 0px'}}><NavHOC /></div>
        <div style={{margin: '20px 0px'}}><NavHooks /></div>

        <div style={{marginLeft: '50px'}}>
          <Switch>
            <Route path='/' exact component={topPage}/>
            <Route path={['/TicTacToe', '/Test']} exact component={TicTacToe}/>
            <Route path='/ApiTutorial' exact component={ApiTutorial}/>
            <Route path='/Othello' exact component={Othello}/>
            <Route path='/PropTypes' exact component={PropTypeRoute}/>
            <Route path='/MaterialUI' exact component={MaterialUI}/>
            <Route path='/Recompose' exact component={Recompose}/>
            <Route path='/ReactKonva' exact component={Konva}/>
            {/* 一番末尾に追加 pathの指定も、対応するLinkの追加も必要ない */}
            <Route exact component={page404}/>
          </Switch>
        </div>
      </div>
    </Router>
  )
}

export default Menu