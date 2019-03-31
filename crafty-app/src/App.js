import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { welcome, getMyProfile, getItems } from './actions';

const Item = ({ author, id, price, name, primaryImagePath }) => (
  <div className="item">
    <div className="item-name">{name}</div>
    <img className="item-image" src={primaryImagePath} />
    <div className="item-price">{price}</div>
  </div>
);

class App extends Component {
  state = { items: [] };

  componentDidMount() {
    welcome().then(res => {
      console.log(res);
      this.setState({ data: res.data });
    });
    getMyProfile({}).then(console.log, e => console.log(e.response, Object.entries(e)));
    getItems().then(res => {
      console.log(res);
      this.setState({ items: res.data.data });
    })
  }
  render() {
    const { data, items } = this.state;

    return (
      <div className="App">
        <img src={logo} className="App-logo" alt="logo" />
        {data && JSON.stringify(data, null, 2)}
        {items.map(e => <Item {...e}/>)}
      </div>
    );
  }
}

export default App;
