import React, { Component } from 'react';
import { register, login } from 'core/actions';

import './Authorization.css';

const Form = ({onSubmit, name}) => (
  <form name={name} className={`${name} Authorization`} onSubmit={onSubmit}>
    <h2>{name}</h2>
    <label>
      <div>Email:</div>
      <input name="email" placeholder="Email"/>
    </label>
    <label>
      <div>Password:</div>
      <input name="password" type="password" placeholder="Password"/>
    </label>
    <button type="submit">{name}</button>
  </form>
)

export class Register extends Component {
  onSubmit = e => {
    e.preventDefault();
    const { email, password } = e.target;
    console.log(register({email: email.value, password: password.value}).catch(console.log))
  }

  render() {
    return <Form name="Register" onSubmit={this.onSubmit}/>;
  }
}

export class Login extends Component {
  onSubmit = e => {
    e.preventDefault();
    const { email, password } = e.target;
    login({email: email.value, password: password.value}).then(console.log, console.error)
  }

  render() {
    return <Form name="Login" onSubmit={this.onSubmit}/>;
  }
}

