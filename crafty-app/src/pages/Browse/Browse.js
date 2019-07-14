import React, { Component } from 'react';
import { getItems, browse, searchByName } from 'core/actions';
import Item from 'components/Item/Item';

import './Browse.css';

class Browse extends Component {
  state = { items: [] };

  updateItems() {
    const { group, subgroup } = this.props.match.params;
    const f = group === 'search' ? searchByName : subgroup ? browse : getItems;
    f(subgroup).then(res => this.setState({ items: res.data }));
  }

  componentDidMount() {
    this.updateItems();
  }
  componentDidUpdate(props) {
    if (this.props.match.url !== props.match.url) {
      this.updateItems();
    }
  }
  render() {
    const { items } = this.state;

    return <div className="Browse">{items.map(e => <Item {...e} key={e.id} />)}</div>
  }
}

export default Browse;
