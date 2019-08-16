## Redux
Reactが扱うUIのstate(状態)を管理をするためのフレームワーク  
Componentに集まっていた概念をStore、Actionに分離するFluxの概念を拡張したもの

- [公式](https://redux.js.org/)

### インストール
`$ yarn add redux react-redux`

### Three Priciples
1. Single source of truth  
Storeを複数作るのでなく、一つのStoreに管理させる

2. State is read-only  
setStateでstateを更新するのでなく、必ずActionを発行して更新する

3. Changes are made with pure functions  
変更する際は純粋な関数を使用する  
(※純粋な関数...Reducer。同じ引数を渡すと必ず同じ結果が返ってくる、副作用のない関数)

### 基本的な使い方
createStoreでStoreを作成し、Storeから取得したstateでdispatchをすることで、渡したアクションタイプに応じたstateの変更が行われる  
store.subscribeすることで、storeの変更を検知し、変更があるたびにレンダーしなおす

Action　←　Reducer
　↑　　　　　↓
View　　←　Store

※Reducerの前に処理を挟みたい場合は、Middlewareを使用する

index.js
```js
import { createStore } from 'redux';
import reducer from './reducers/';

const store = createStore(reducer);

const render = () => {
  const state = store.getState();
  console.log(state);
  ReactDOM.render(
    <App 
      place={state.place}
      onPlaceChange={place => state.dispatch({ type: 'CHANGE_PLACE', place })}
    />,
    document.getElementById('root')
  );
};

render();
store.subscribe(render);
```

reducers/index,js
```js
export default (state = { place: 'hoge' }, action) => {
  switch (action.type) {
    case 'CHANGE_PLACE':
      return Object.assign({}, state, { place: action.place }); // からのハッシュにstateをマージする placeの値を更新する
      break;
    default:
      return state;
  }
}
```

### 子コンポーネントでそれぞれサブスクライブする
子コンポーネントにstoreを渡し、子コンポーネント側でcomponentDidMountでforceUpdateする  
store.subscribeの戻り値はsubscribeを解除するのに使用できる

index.js
```js
ReactDOM.render(
　<ReduxTutorial
    history={window.history}
    location={window.location}
    store={createStore(reducer)}
  />,
  document.getElementById('root')
);
```

ReduxTutorial.js
```js
  // ページ描画時に実行される
  componentDidMount() {
    this.unsubscribe = this.props.store.subscribe(() => {
      this.forceUpdate();
    })
  }

  // ページがなくなる時に実行される
  componentWillUnmount() {
    this.unsubscribe();
  }
```

### Storeを子コンポーネントで共有する
reactのcontextを使用した仕組み  
この仕組みを利用した場合、サブスクライブは自動で設定してくれるので手動でforceUpdateの処理を書かなくて良い

index.js
Providerを使用してstoreをセットする
```js
import { Provider } from 'react-redux';
.
.
.
ReactDOM.render(
  <Provider store={createStore(reducer)}>
  　<ReduxTutorial
      history={window.history}
      location={window.location}
    />
  </Provider>,
  document.getElementById('root')
);
```

SearchForm.js
connectを使用してStoreと連結して、propsとして使用できるようにする（以下の場合はstoreとonPlaceChange）
mapDispatchToPropsのような、dispatchの処理をactionsに分離して使用する方法もある
```js
import { connect } from 'react-redux';
.
.
.
const mapStateToProps = state => ({
  place: state.place,
})
const mapDispatchToProps = dispatch => ({
  onPlaceChange: place => dispatch({ type: 'CHANGE_PLACE', place })
});
.
.
.
const ConnnectedSearchForm = connect(mapStateToProps, mapDispatchToProps)(SearchForm);

export default ConnnectedSearchForm;
```

※上記のmapDispatchToPropsのような、dispatchの処理をactionsに分離して使用する方法もある

actions/index.js
```js
export const setPlace = place => dispatch => dispatch({ type: 'CHANGE_PLACE', place});
```

SearchForm.js
```js
import { setPlace } from '../actions/';
.
.
.
export default connect(
  state => ({
    place: state.place,
  }),
  { setPlace }
)(SearchForm);
```

### Middlewareを使用する例
なお、ミドルウェアはカンマ区切りで複数指定可能
[公式 Midoleware](https://redux.js.org/advanced/middleware)
```js
import thunk from 'redux-thunk';

const store = createStore(
  reducer,
  composeWithDevTools(applyMiddleware(thunk)),
);
```