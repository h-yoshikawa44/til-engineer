import React from 'react';
import PropTypes from 'prop-types';

const PropTypesComponent = props => {
  return (
    <h2>Hello {props.name}</h2>
  );
}

PropTypesComponent.propTypes = {
  name: PropTypes.string
};

export default PropTypesComponent;