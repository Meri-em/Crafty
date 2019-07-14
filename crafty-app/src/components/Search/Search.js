import React, { Component } from 'react';

import './Search.css';

class Search extends Component {
  state = { value: '' };
  onChange = e => this.setState({ value: e.target.value });
  onSubmit = e => {
    const { value } = this.state;
    e.preventDefault();
    window.location.hash = value ? '/search/' + encodeURIComponent(value) : '';
  };

  render() {
    return (
      <form onSubmit={this.onSubmit} className="Search">
        <input value={this.state.value} onChange={this.onChange} placeholder="Търси ръчни изработки"/>
      </form>
    );
  }
}

export default Search;