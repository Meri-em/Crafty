import React, { Component } from 'react';
import { getCart, removeFromCart } from 'core/actions';

import './Cart.css';
const add = (arr, item) => (arr.push(item), item); // eslint-disable-line no-sequences

class Cart extends Component {
  state = {total: 0, items: []};

  componentDidMount() {
    getCart().then(res => this.setState({...res.data}));
  }
  render() {
    const { items } = this.state;
    const groups = []
    items.forEach(({item, quantity}) => {
      const author = item.author;
      const group = groups.find(e => e.author.id === author.id) || add(groups, {author, items: []});
      group.items.push({item, quantity});
    })
    return (
      <div className="cart-page">
        <h1>Cart contents</h1>
        <div className="cart-items">
          {groups.map(({author, items}) => (
            <div className="cart-group" key={author.id}>
              <a className="cart-author">
                <img className="cart-author-image" src={author.image} alt={author.name} />
                <div className="cart-author-name">{author.name}</div>
              </a>
              <div className="cart-author-items">
                {items.map(({item, quantity}) => (
                  <div className="cart-item" key={item.id}>
                    <img className="cart-item-image" src={item.image} alt={item.name} />
                    <div className="cart-item-info">
                      <a className="cart-item-name" href={'#/_/' + item.id}>{item.name}</a>
                      <div className="cart-item-price">{item.price}$</div>
                      <div className="cart-item-quantity">x{quantity}</div>
                      <div className="cart-item-remove" onClick={() => removeFromCart({id: item.id})}>Remove item</div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div>Total {this.state.total}</div>
        <div>Proceed to payment</div>
      </div>
    );
  }
}

export default Cart;