import React from 'react';
import { Link } from 'react-router-dom';

import './Navigation.css';

const convert = obj => {
  const [ href, data ] = Object.entries(obj)[0];
  const items = (data.items || []).map(convert);
  items.forEach(e => e.href = `${href}/${e.href}`)
  return { href, text: data.text, items };
};
const menu = require('../../navigation.json').map(convert);

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

const Navigation = () => (
  <div className="Navigation">
    {menu.map((e, i) => <Group {...e} key={i} />)}
  </div>
);

export default Navigation;