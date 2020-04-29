import React from 'react';
import { withState, withHandlers } from 'recompose';

const stateEnhance = withState('counter', 'setCounter', 0);

const handleEnhance = withHandlers({
  incrementCounter: props => () => {
    props.setCounter(v => v + 1)
  },
  decrementCounter: props => () => {
    props.setCounter(v => v - 1)
  },
});

const Component = stateEnhance(handleEnhance((
  { counter, incrementCounter, decrementCounter }) => {
  return (
    <div>
      <p>カウンター: {counter}</p>
      <button onClick={() => incrementCounter()}>Increment</button>
      <button onClick={() => decrementCounter()}>Decrement</button>
    </div>
  );
}));

export default Component;