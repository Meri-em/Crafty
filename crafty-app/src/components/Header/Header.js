import React from 'react';
import Navigation from 'components/Navigation/Navigation';
import Search from 'components/Search/Search';
import { connect } from 'core/store';
import { logout } from 'core/actions';

import './Header.css';

const Header = ({isLoggedIn}) => (
  <header>
    <img src="/logo.svg" className="App-logo" alt="logo" />
    <Search />
    {!isLoggedIn && <nav className="AuthNav">
      <a href="#/login">login</a>
      <a href="#/register">register</a>
    </nav>}
    {isLoggedIn && <nav className="AuthNav">
      <a href="#/myProfile">myProfile</a>
      <a href="#/myCart">myCart</a>
      <a href="#/logout" onClick={logout}>logout</a>
    </nav>}
    <Navigation />
  </header>
);

export default connect(({isLoggedIn}) => ({isLoggedIn}))(Header);