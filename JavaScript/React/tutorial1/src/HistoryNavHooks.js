import React from 'react';
import { useHistory } from 'react-router-dom';

const Nav = props => {
  const history = useHistory();
  const handleLink = path => history.push(path);
  return (
    <nav>
      <button onClick={() => handleLink('/about')}>About</button>
      <button onClick={() => handleLink('/users')}>Users</button>
      <button onClick={() => handleLink('/')}>Home</button>
    </nav>
  );
}

export default Nav;