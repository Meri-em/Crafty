import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { Register, Login } from 'components/Authorization/Authorization';
import Browse from 'pages/Browse/Browse';
import Profile from 'pages/Profile/Profile';
import MyOrders from 'pages/MyOrders/MyOrders';
import Item from 'pages/Item/Item';
import AddItem from 'pages/AddItem/AddItem';
import Cart from 'pages/Cart/Cart';

const Routes = () => (
  <Switch>
    <Route path="/profile" component={Profile} />
    <Route path="/myOrders" component={MyOrders} />
    <Route path="/myCart" component={Cart} />
    <Route path="/add" component={AddItem} />
    <Route path="/register" component={Register} />
    <Route path="/login" component={Login} />
    <Route path="/_/:id/:edit?" component={Item} />
    <Route path="/:group?/:subgroup?" component={Browse} />
  </Switch>
);

export default Routes;
