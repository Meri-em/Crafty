import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';


class Sasa extends Component {
	render(){
		return "This is SASA";
	}
}

const Baba  = () =>  "This is BABA";

class App extends Component {
  componentDidMount() {
    axios.get('http://localhost:8080/api/v1/welcome').then((res) => {
      console.log(res);
    })
  }
  render() {
	  const result = (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
		  <Sasa />
		  <Baba />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    );
	console.log(result);
    return result;
  }
}

export default App;
