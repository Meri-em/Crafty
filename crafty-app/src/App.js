import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { welcome } from './actions';

class App extends Component {
  state = {};

  componentDidMount() {
    welcome().then(res => {
      console.log(res);
      this.setState({ data: res.data });
    })
  }
  render() {
    const { data } = this.state;

    return (
      <div className="App">
        <img src={logo} className="App-logo" alt="logo" />
        {data && JSON.stringify(data, null, 2)}
      </div>
    );
  }
}

export default App;
