import React, { Component } from 'react'
import { LS } from './utils';

const StoreContext = React.createContext();

export const createStore = WrappedComponent => {
  return class extends Component {
    state = window.store = {
      get: (key, fallback) => this.state[key] || fallback,
      set: (key, value) => {
        const state = this.state;
        state[key] = value;
        this.setState(state);
      },
      remove: key => {
        const state = this.state;
        delete state[key];
        this.setState(state);
      },
      isLoggedIn: (LS.get('tokens') || {}).refreshExpires > Date.now()
    }
    render() {
      return (
        <StoreContext.Provider value={this.state}>
          <WrappedComponent {...this.props} />
        </StoreContext.Provider>
      )
    }
  }
}

export const withStore = WrappedComponent => props => (
  <StoreContext.Consumer>
    {context => <WrappedComponent store={context} {...props} />}
  </StoreContext.Consumer>
);

export const connect = map => WrappedComponent => props =>(
  <StoreContext.Consumer>
    {context => <WrappedComponent {...map(context)} {...props} />}
  </StoreContext.Consumer>
);

