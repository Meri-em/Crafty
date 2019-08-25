import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { FaPen, FaCartPlus } from 'react-icons/fa';
import { addToCart, updateDefaultImage, deleteItemImage } from 'core/actions';
import { getUser } from 'core/utils';
import { ReviewList } from '../Review/Review';
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
    const itemId = id;
    const image = this.state.image || this.props.image;
    const isMine = author.id === getUser().id;
    const isEdit = isMine && location.hash.endsWith('/edit'); // eslint-disable-line

    return (
      <div className={`Item large ${isEdit ? 'edit' : 'view'}`} id={id}>
        <div className="item-name">{name}</div>
        {isMine && <Link className="item-edit-link action" to={`/_/${id}${isEdit ? '' : '/edit'}`} ><FaPen title="Редактирай" /></Link>}
        <div className="item-preview">
          <img className="item-image" src={image} alt="" />
          <div className="item-thumbnails">
            {images.map(({ path, id }) => (
              <div className="item-thumbnail" key={id}>
                {isEdit && <>
                  <span className="image-make-default" title="сложи на първо място" onClick={() => updateDefaultImage({itemId, imageId: id})} >[1]</span>
                  <span className="image-remove" title="премахни картинката" onClick={() =>  deleteItemImage({itemId, imageId: id})} >&times;</span>
                </>}
                <img className="item-thumbnail-image" src={path} alt="" onClick={() => this.setState({ image: path })} />
              </div>
            ))}
          </div>
        </div>
        <div className="item-price">{price}</div>
        <div className="item-add" onClick={() => addToCart({id})}><FaCartPlus title="Добави в количката" /></div>
        <div className="item-author">{author.name}</div>
        <div className="item-description">{description}</div>
        <ReviewList itemId={itemId}/>
      </div>
    )
  }
}

export default ItemSimple;

export { ItemSimple, ItemDetailed };