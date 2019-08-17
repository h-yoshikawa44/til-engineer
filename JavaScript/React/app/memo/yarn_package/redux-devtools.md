## redux-devtools

[公式](http://extension.remotedev.io/)

### インストール
以下の両方でインストールする

- コマンドライン上でインストール
  `$ yarn add redux-devtools`

- ブラウザでインストール
[Chrome拡張](https://chrome.google.com/webstore/detail/redux-devtools/lmhkpmbekcpmknklioeibfkpmmfibljd)

※middlewareと一緒に使用する場合は、`redux-devtools-extension`をyarnでインストールする

### 使い方
createStoreの第２引数を指定する
```js
const store = createStore(
  reducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
);

ReactDOM.render(
  <Provider store={store}>
  　<ReduxTutorial
      history={window.history}
      location={window.location}
    />
  </Provider>,
  document.getElementById('root')
);
```

Chrome拡張のアイコンから起動

#### middlewareと一緒に使用する場合
```js
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
.
.
.
const store = createStore(
  reducer,
  composeWithDevTools(applyMiddleware(thunk)),
);
```