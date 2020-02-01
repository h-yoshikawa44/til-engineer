import React from 'react';
import { withRouter } from 'react-router';

const Nav = props => {
  const handleLink = path => props.history.push(path);
  return (
    <nav>
      <button onClick={() => handleLink('/about')}>About</button>
      <button onClick={() => handleLink('/users')}>Users</button>
      <button onClick={() => handleLink('/')}>Home</button>
    </nav>
  );
}

export default withRouter(Nav);