// デフォルトコード
import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';

import './index.css';
// import App from './App';
import Menu from './Menu';
import RouterParam from './Library/ReactRouter/RouterParam';
import * as serviceWorker from './serviceWorker';
import reducer from './Redux-tutorial/reducers'

const store = createStore(
  reducer,
  composeWithDevTools(applyMiddleware(thunk)),
);

ReactDOM.render(
  <Provider store={store}>
    <Menu />
    <hr style={{marginTop: '100px'}}/>
  　<RouterParam />
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
