import React from 'react';
import { withRouter } from 'react-router';

const Nav = props => {
  const handleLink = path => props.history.push(path);
  return (
    <nav>
      <button onClick={() => handleLink('/TicTacToe')}>三目並べ</button>
      <button onClick={() => handleLink('/ReduxTutorial')}>緯度経度検索</button>
      <button onClick={() => handleLink('/ApiTutorial')}>APIチュートリアル</button>
    </nav>
  );
}

export default withRouter(Nav);