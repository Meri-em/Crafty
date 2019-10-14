import React, { Component } from 'react';
import { getItems, browse, searchByName, getNavigation } from 'core/actions';
import { getSubCategories } from 'core/utils';
import Item from 'components/Item/Item';

import './Browse.css';

class Browse extends Component {
  state = { items: [] };

  updateItems() {
    const { group, subgroup } = this.props.match.params;
    console.log(getSubCategories(group));
    let f = group === 'search' ? searchByName : !group ? getItems : subgroup ? browse : null;
    f = f || (() => getNavigation().then(() => browse(getSubCategories(group.toUpperCase()).join(','))));
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
