import React, { Component } from 'react';
import { getItem } from 'actions';
import { ItemDetailed } from 'components/Item/Item';


class Item extends Component {
  state = { };

  componentDidMount() {
    getItem(this.props.match.params.id).then(res => {
      this.setState({ item: res.data });
    })
  }
  render() {
    const { item } = this.state;

    return !item || <ItemDetailed {...item} />;
  }
}

export default Item;