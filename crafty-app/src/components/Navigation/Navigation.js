import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { getNavigation } from 'core/actions';

import './Navigation.css';

const convert = (obj, baseHref='') => {
  const href = `${baseHref}/${obj.href}`.toLowerCase();
  return { ...obj, href, items: (obj.items || []).map(e => convert(e, href))};
};

const Group = ({ href, text, items }) => (
  <div className="NavGroup">
    <Link to={href}>{text}</Link>
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
    console.log(data.map(e => convert(e)));
      this.setState({
        menu: data.map(e => convert(e))
      });
    });
  }

  render() {
    const { menu } = this.state;

    return (
      <div className={`Navigation mobile-${this.state.on ? 'on' : 'off'}`}>
        <a className="mobile-menu" href="#/" onClick={e => {
          e.preventDefault();
          this.setState({ on: !this.state.on });
        }}> </a>
        {menu.map((e, i) => <Group {...e} key={i} />)}
      </div>
    )
  }
}

export default Navigation;