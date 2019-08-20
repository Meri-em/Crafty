import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Browse from 'pages/Browse/Browse';
import MyProfile from 'pages/MyProfile/MyProfile';
import MyOrders from 'pages/MyOrders/MyOrders';
import Item from 'pages/Item/Item';
import EditItem from 'pages/EditItem/EditItem';
import { Register, Login } from 'components/Authorization/Authorization';
import Cart from 'components/Cart/Cart';

const Routes = () => (
  <Switch>
    <Route path="/myProfile" component={MyProfile} />
    <Route path="/myOrders" component={MyOrders} />
    <Route path="/myCart" component={Cart} />
    <Route path="/edit" component={EditItem} />
    <Route path="/register" component={Register} />
    <Route path="/login" component={Login} />
    <Route path="/_/:id" component={Item} />
    <Route path="/:group/:subgroup" component={Browse} />
    <Route path="/:group" component={Browse} />
    <Route path="/" component={Browse} />
  </Switch>
);

export default Routes;
