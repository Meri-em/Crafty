import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { FaPen, FaCartPlus, FaArchive, FaTrashRestoreAlt } from 'react-icons/fa';
import { addToCart, updateDefaultImage, deleteItemImage, deleteItem, restoreItem } from 'core/actions';
import { addItem } from 'core/actions';
import { ReviewList } from '../Review/Review';
import './Item.css';

const ItemSimple = ({ author, id, price, name, image, edit, archived }) => (
  <Link className="Item thumbnail" to={`/_/${id}`} style={{ backgroundImage: 'url(' + image + ')' }}>
    <div className="item-name">{name}</div>
    <div className="item-price">{price}</div>
    {edit && !archived && <div className="action" onClick={e => { e.preventDefault(); deleteItem(id); }}><FaArchive title="Архивирай"/></div>}
    {edit && archived && <div className="action" onClick={e => { e.preventDefault(); restoreItem(id); }}><FaTrashRestoreAlt title="Възстанови"/></div>}
  </Link>
);

class ItemDetailed extends Component {
  state = {};

  checkEditMode = () => this.props.isMine && location.hash.endsWith('/edit'); // eslint-disable-line

  renderCategorySelect = (value) => (
    <select name="category" defaultValue={value}>
      {window.store.get('categories', []).map(g => (
        <optgroup label={g.text} key={g.href}>
          {g.items.map(e => <option value={e.href}>{e.text}</option>)}
        </optgroup>
      ))}
    </select>
  )
  renderField(field, type='text') {
    const [name, value] = Object.entries(field)[0];
    if (!this.checkEditMode()) {
      return value;
    }
    if (name === 'category') {
      console.log(window.store.get('categories'));
      return this.renderCategorySelect(value);
    }
    return type === 'textarea'? <textarea name={name} defaultValue={value}/> : <input name={name} type={type} defaultValue={value}/>;
  }

  onSubmit = e => {
    e.preventDefault();
    const { id } = this.props; 
    addItem({ url: `/api/v1/items/${id}`, data: new FormData(e.target)}).then(res => {
      location.hash = `/_/${id}`; // eslint-disable-line
      console.log(res);
    });
  }

  render() {
    const { author, price, name, category, images, description, id, isMine } = this.props;
    const itemId = id;
    const image = this.state.image || this.props.image;
    const isEdit = this.checkEditMode();
    const Wrapper = isEdit ? 'form' : 'div';

    return (
      <Wrapper className={`Item large ${isEdit ? 'edit' : 'view'}`} id={id} onSubmit={this.onSubmit}>
        <div className="item-name">{this.renderField({name})}</div>
        {isEdit && <input name="id" value={id} type="hidden"/>}
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
        <div className="item-actions">
          <div className="item-price">{this.renderField({price}, 'number')}</div>
          {!isMine && <>
            <div className="item-add" onClick={() => addToCart({id})}><FaCartPlus title="Добави в количката" /></div>
            <Link className="item-author" to={`/profile/${author.id}`}>{author.name}</Link>
          </>}
          <div className="item-category">{this.renderField({category})}</div>
        </div>
        <div className="item-description">{this.renderField({description}, 'textarea')}</div>
        {isEdit ? <button className="item-save">Запази</button> : <ReviewList itemId={itemId}/>}
      </Wrapper>
    )
  }
}

export default ItemSimple;

export { ItemSimple, ItemDetailed };