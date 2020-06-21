import React from 'react';
import { compose, withState, withHandlers, lifecycle, mapProps } from 'recompose';

const enhance = compose(
  withState('num1', 'setNum1', 10),
  withState('num2', 'setNum2', 20),
  mapProps(props => {
    return {
      sum: props.num1 + props.num2
    }
  }),
  withState('text', 'setText', ''),
  withState('counter', 'setCounter', 5),
  withHandlers({
    testText: props => () => {
      props.setText('テスト');
    },
    incrementCounter: props => () => {
      props.setCounter(v => v + 1)
    },
    decrementCounter: props => () => {
      props.setCounter(v => v - 1)
    },
    resetCounter: props => () => {
      props.setCounter(0)
    }
  }),
  lifecycle({
    componentDidMount() {
      this.props.testText();
    }
  })
)

const ComposeComponent = enhance(
  ({
    sum,
    text,
    counter,
    incrementCounter,
    decrementCounter,
    resetCounter
  }) => {
    return (
      <div>
        <p>{sum}</p>
        <p>{text}</p>
        <p>カウンター：{counter}</p>
        <button onClick={() => incrementCounter()}>Increment</button>
        <button onClick={() => decrementCounter()}>Decrement</button>
        <button onClick={() => resetCounter()}>Reset</button>
      </div>
    )
})

export default ComposeComponent;