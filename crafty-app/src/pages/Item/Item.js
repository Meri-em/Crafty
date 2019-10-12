import React, { Component } from 'react';
import { getUser } from 'core/utils';
import { getItem } from 'core/actions';
import { ItemDetailed } from 'components/Item/Item';


class Item extends Component {
  state = { };

  fetchItem = () => getItem(this.props.match.params.id).then(res => this.setState({ item: res.data }));
  componentDidMount() {
    this.fetchItem();
  }
  componentDidUpdate({match}) {
    if (this.props.match.params.id !== match.params.id || !!this.props.match.params.edit < !!match.params.edit) {
      this.fetchItem();
    }
  }
  
  render() {
    const { item } = this.state;
    const isMine = item && item.author.id === getUser().id;
    const isEdit = isMine && this.props.match.params.edit;

    return !item || <ItemDetailed {...item} isMine={isMine} isEdit={isEdit} />;
  }
}

export default Item;