import React from 'react';
import PropTypes from 'prop-types';

const PropTypesComponent = props => {
  return (
    <div>
      <p>number： {props.num}</p>
      <p>string： {props.str}</p>
      <p>bool：　{props.bl ? 'true' : 'false'}</p>
      <p>array：　{props.arr.map(data => data.id)}</p>
      <p>arrayOf：　{props.arrof.map(data => data)}</p>
      <p>object：　{props.obj.a}、{props.obj.b}</p>
      <p>objectOf：　{props.objof.a}、{props.objof.b}</p>
      <p>shape：　{props.shape.num}、{props.shape.str}</p>
      <p>exact：　{props.exact.num}、{props.exact.str}</p>
      <button onClick={props.func}>func</button>
      <p>symbol：　{props.symbol.toString()}</p>
      <p>oneOf：　{props.oneof}</p>
      <p>types：　{props.types}</p>
      <p>class：　{typeof props.class}</p>
      {props.element}
      {props.elementType}
      {props.node.a}
      <p>custom：　{props.custom}</p>
      <p>defaultValue：　{props.defaultValue}</p>
    </div>
  );
}

const customProp = (props, propName, componentName) => {
  if (!/test/.test(props[propName])) {
    return new Error(
      'Invalid prop `' + propName + '` supplied to' +
      ' `' + componentName + '`. Validation failed.'
    );
  }
}

PropTypesComponent.propTypes = {
  num: PropTypes.number,
  str: PropTypes.string,
  bl: PropTypes.bool,
  arr: PropTypes.array,
  arrof: PropTypes.arrayOf(PropTypes.number),
  obj: PropTypes.object,
  objof: PropTypes.objectOf(PropTypes.number),
  shape: PropTypes.shape({
    num: PropTypes.number,
    str: PropTypes.string
  }),
  exact: PropTypes.exact({
    num: PropTypes.number,
    str: PropTypes.string
  }),
  func: PropTypes.func,
  symbol: PropTypes.symbol,
  oneof: PropTypes.oneOf(['A', 'B', 'C']),
  types: PropTypes.oneOfType([
    PropTypes.number,
    PropTypes.string
  ]),
  class: PropTypes.instanceOf(Date),
  element: PropTypes.element,
  elementType: PropTypes.elementType,
  node: PropTypes.node,
  custom: customProp
};

PropTypesComponent.defaultProps = {
  defaultValue: 'default'
}

export default PropTypesComponent;