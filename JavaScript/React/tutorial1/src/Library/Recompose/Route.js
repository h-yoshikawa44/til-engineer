import React from 'react';
import Component from './Component';
import ComposeComponent from './ComposeComponent';

const Route = () => {
  return (
    <React.Fragment>
      <Component />
      <ComposeComponent />
    </React.Fragment>
  )
}

export default Route;