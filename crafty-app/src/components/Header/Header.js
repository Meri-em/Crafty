import React from 'react';
import Navigation from 'components/Navigation/Navigation';
import Search from 'components/Search/Search';
import { connect } from 'core/store';
import { logout } from 'core/actions';
import { FaUserPlus, FaSignInAlt, FaUser, FaShoppingCart, FaTruckLoading, FaSignOutAlt } from 'react-icons/fa';

import './Header.css';

const Header = ({isLoggedIn}) => (
  <header>
    <img src="/logo.svg" className="App-logo" alt="logo" />
    <Search />
    {!isLoggedIn && <nav className="AuthNav">
      <a href="#/login"><FaSignInAlt/> Вход</a>
      <a href="#/register"><FaUserPlus/> Регистрация</a>
    </nav>}
    {isLoggedIn && <nav className="AuthNav">
      <a href="#/profile"><FaUser/> Профил</a>
      <a href="#/myOrders"><FaTruckLoading/> Поръчки</a>
      <a href="#/myCart"><FaShoppingCart/> Количка</a>
      <a href="#/logout" onClick={logout}><FaSignOutAlt/> Изход</a>
    </nav>}
    <Navigation />
  </header>
);

export default connect(({isLoggedIn}) => ({isLoggedIn}))(Header);