import React from 'react';
import MaterialUIHooks from './MaterialUIHooks';
import MaterialUIStyled from './MaterialUIStyled';
import MaterialUIHOC from './MaterialUIHOC';

const Route = () => {
  return (
    <React.Fragment>
      <MaterialUIHooks />
      <MaterialUIStyled />
      <MaterialUIHOC />
    </React.Fragment>
  )
}

export default Route;