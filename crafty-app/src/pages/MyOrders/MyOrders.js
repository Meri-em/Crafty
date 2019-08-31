import React, { Component } from 'react';
import { getOrders } from 'core/actions';

import './MyOrders.css';

class MyOrders extends Component {
  state = { data: [] };

  componentDidMount() {
    getOrders().then(({data}) => this.setState({data}));
  }
  renderOrder = ({total, items, createdAt}, i) => (
    <div className="order" key={i}>
      <span className="date">{new Date(createdAt.replace(/\..*/, '')).toLocaleString()}</span>
      <div className="item-list">
        {items.map(({item, paid, quantity}) => (
          <div className="item" key={item.id}>
            <strong className="item-name">{item.name}</strong>
            <img className="item-img" src={item.image} alt={item.name}/>
            <span className="item-price">{quantity > 1 ? quantity + ' x ': ''}{paid}</span>
          </div>
        ))}
      </div>
      <span className="total">Общо: {total}</span>
    </div>
  );
  render() {
    const { sales=[], purchases=[] } = this.state.data;
    // return <pre className="MyOrders">{JSON.stringify(this.state.data, null, 2)}</pre>
    return (
      <div className="MyOrders">
        <div className="MyPurchases">
          <h2>Покупки</h2>
          {purchases.map(this.renderOrder)}
        </div>
        <div className="MySales">
          <h2>Продажби</h2>
          {sales.map(this.renderOrder)}
        </div>
      </div>
    )
  }
}

export default MyOrders;
