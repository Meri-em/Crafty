import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter as Router } from 'react-router-dom';
import Header from './components/Header/Header';
import Notification from './components/Notification/Notification';
import Routes from './core/routes';
import { createStore } from './core/store';

import './index.css';

const App = createStore(() => (
  <div className={`App ${window.store.get('darkMode') ? 'dark' : 'light'}`}>
    <Router>
      <Header/>
      <Notification />
      <Routes/>
    </Router>
  </div>
));

window.addEventListener('hashchange', () => window.store.set('message', ''));

ReactDOM.render(<App />, document.getElementById('root'));
