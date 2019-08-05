## Redux
Reactが扱うUIのstate(状態)を管理をするためのフレームワーク  
Componentに集まっていた概念をStore、Actionに分離するFluxの概念を拡張したもの

- [公式](https://redux.js.org/)

### Three Priciples
1. Single source of truth  
Storeを複数作るのでなく、一つのStoreに管理させる

2. State is read-only  
setStateでstateを更新するのでなく、必ずActionを発行して更新する

3. Changes are made with pure functions  
変更する際は純粋な関数を使用する  
(※純粋な関数...Reducer。同じ引数を渡すと必ず同じ結果が返ってくる、副作用のない関数)