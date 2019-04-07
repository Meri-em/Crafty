import React, { Component } from 'react';
import { getItems, browse } from 'actions';
import Item from 'components/Item/Item';

import './Browse.css';

class Browse extends Component {
  state = { items: [] };

  updateItems() {
    const page = this.props.match.params.subgroup;
    (page ? browse(page) : getItems()).then(res => {
      this.setState({ items: res.data });
    })
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
