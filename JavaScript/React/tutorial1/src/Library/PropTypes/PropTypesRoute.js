import React from 'react';
import PropsTypesComponent from './PropTypesComponent';
// import { Test, Test2 } from './Test';

const arr = [
  {
    id: 'A'
  },
  {
    id: 'B'
  }
];
const obj = {
  a: 'A',
  b: 'B'
};
const objof = {
  a: 1,
  b: 2
};
const shape = {
  num: 1,
  str: '太郎',
  bool: true
};
const exact = {
  num: 1,
  str: '太郎',
  // bool: true 警告をだす
};
const func = () => {
  console.log('func');
}
const App = () => {
  return (
    <PropsTypesComponent
      num={1}
      str="1"
      bl={true}
      arr={arr}
      arrof={[1, 2]}
      obj={obj}
      objof={objof}
      shape={shape}
      exact={exact}
      func={func}
      symbol={Symbol('test')}
      oneof={'A'}
      types={1}
      class={new Date()}
      element={<p>Test</p>}
      // elementType={<Test />}
      node="node"
      custom="abctest"
    />
  )
}

export default App;