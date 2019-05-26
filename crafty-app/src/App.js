import React, { Component } from 'react';
import { Route, Switch, HashRouter as Router } from 'react-router-dom';
import './App.css';
import Navigation from './components/Navigation/Navigation';
import Search from './components/Search/Search';
import Browse from './pages/Browse/Browse';
import Item from './pages/Item/Item';
import EditItem from './pages/EditItem/EditItem';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Router>
          <header>
            <img src="/logo.svg" className="App-logo" alt="logo" />
            <Search />
            <Navigation />
          </header>
          <Switch>
            <Route path="/edit" component={EditItem} />
            <Route path="/_/:id" component={Item} />
            <Route path="/:group/:subgroup" component={Browse} />
            <Route path="/:group" component={Browse} />
          </Switch>
        </Router>
      </div>
    );
  }
}

export default App;
