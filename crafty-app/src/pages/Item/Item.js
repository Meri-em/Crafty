import React, { Component } from 'react';
import { getItem } from 'core/actions';
import { ItemDetailed } from 'components/Item/Item';


class Item extends Component {
  state = { };

  componentDidMount() {
    const { id } = this.props.match.params;
    getItem(id).then(res => {
      this.setState({ item: res.data });
    });
  }
  render() {
    const { item } = this.state;

    return !item || <ItemDetailed {...item} />;
  }
}

export default Item;