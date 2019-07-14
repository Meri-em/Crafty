import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter as Router } from 'react-router-dom';
import Header from './components/Header/Header';
import Routes from './core/routes';
import { createStore } from './core/store';

import './index.css';

const App = createStore(() => (
  <div className="App">
    <Router>
      <Header/>
      <Routes/>
    </Router>
  </div>
));

ReactDOM.render(<App />, document.getElementById('root'));
