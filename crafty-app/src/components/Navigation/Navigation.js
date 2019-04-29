import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { getNavigation } from 'actions';

import './Navigation.css';

const convert = (obj, baseHref='') => {
  obj.href = `${baseHref}/${obj.href}`.toLowerCase();
  obj.items = (obj.items || []).map(e => convert(e, obj.href));
  return obj;
};

const Group = ({ href, text, items }) => (
  <div className="NavGroup">
    <Link to={'/' + href}>{text}</Link>
    {!items.length || (
      <nav>
        {items.map((e, i) => <Group {...e} key={i} />)}
      </nav>
    )}
  </div>
);

class Navigation extends Component {
  state = { menu: [] };

  componentDidMount() {
    getNavigation().then(({ data }) => {
      this.setState({
        menu: data.map(e => convert(e))
      });
    });
  }

  render() {
    const { menu } = this.state;

    return (
      <div className={`Navigation mobile-${this.state.on ? 'on' : 'off'}`}>
        <a className="mobile-menu" onClick={() => this.setState({ on: !this.state.on })}></a>
        {menu.map((e, i) => <Group {...e} key={i} />)}
      </div>
    )
  }
}

export default Navigation;