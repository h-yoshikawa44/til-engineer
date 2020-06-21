import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useRouteMatch
} from 'react-router-dom';

const App = () => {
  return (
    <Router>
      <div>
        <ul>
          <li>
            <Link to='/'>Home</Link>
          </li>
          <li>
            <Link to='/topics'>Topics</Link>
          </li>
        </ul>

        <Switch>
          <Route path='/topics'>
            <Topics />
          </Route>
          <Route path='/'>
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

const Home = () => {
  return <h2>Home</h2>;
}

const Topics = () => {
  const match = useRouteMatch();
  console.log(match);

  return (
    <div>
      <h2>Topics</h2>

      <ul>
        <li>
          <Link to={`${match.url}/components`}>Components</Link>
        </li>
        <li>
          <Link to={`${match.url}/props-v-state`}>
            Props v. State
          </Link>
        </li>
      </ul>

      <Switch>
        <Route path={`${match.path}/components`}>
          <h3>Components</h3>
        </Route>
        <Route path={`${match.path}/props-v-state`}>
          <h3>props-v-state</h3>
        </Route>
      </Switch>
    </div>
  );
}

export default App;