import React, { Component, useState } from 'react';
import { Link } from 'react-router-dom';
import { getNavigation } from 'core/actions';

import './Navigation.css';

const convert = (obj, baseHref='') => {
  const href = `${baseHref}/${obj.href}`.toLowerCase();
  return { ...obj, href, items: (obj.items || []).map(e => convert(e, href))};
};

const Toggle = ({ className, children }) => {
  const [open, setOpen] = useState(false);
  return <button className={`${className} menu-toggle ${open ? 'on' : 'off'}`} aria-expanded={open} aria-haspopup="true" onClick={() => setOpen(!open)}>{children}</button>
}
const Group = ({ href, text, items }) => (
  <div className="NavGroup">
    <Link to={href}>{text}</Link>
    {!items.length || <>
      <Toggle/>
      <nav>
        {items.map((e, i) => <Group {...e} key={i} />)}
      </nav>
    </>}
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
      <div className={`Navigation`}>
        <Toggle className="mobile-menu"/>
        {menu.map((e, i) => <Group {...e} key={i} />)}
      </div>
    )
  }
}

export default Navigation;