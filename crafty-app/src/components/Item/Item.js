import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { addToCart } from 'core/actions';

import './Item.css';

const ItemSimple = ({ author, id, price, name, image }) => (
  <Link className="Item thumbnail" to={`/_/${id}`} style={{ backgroundImage: 'url(' + image + ')' }}>
    <div className="item-name">{name}</div>
    <div className="item-price">{price}</div>
  </Link>
);

class ItemDetailed extends Component {
  state = {};

  render() {
    const { author, price, name, images, description, id } = this.props;
    const image = this.state.image || this.props.image;

    return (
      <div className="Item large" id={id}>
        <div className="item-name">{name}</div>
        <div className="item-preview">
          <img className="item-image" src={image} alt="" />
          <div className="item-thumbnails">
            {images.map(({ path, id }) => (
              <img className="item-thumbnail" src={path} key={id} alt="" onClick={() => this.setState({ image: path })} />
            ))}
          </div>
        </div>
        <div className="item-price">{price}</div>
        <div className="item-add" onClick={() => addToCart({id})}>Add to cart</div>
        <div className="item-author">{author.name}</div>
        <div className="item-description">{description}</div>
      </div>
    )
  }
}

export default ItemSimple;

export { ItemSimple, ItemDetailed };