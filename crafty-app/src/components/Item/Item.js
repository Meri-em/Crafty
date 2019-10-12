import React, { Component } from 'react';
import CrossfadeImage from 'react-crossfade-image';
import { Link } from 'react-router-dom';
import { FaPen, FaCartPlus, FaArchive, FaTrashRestoreAlt, FaArrowsAlt, FaTimes } from 'react-icons/fa';
import { addToCart, deleteItem, restoreItem } from 'core/actions';
import { addItem } from 'core/actions';
import { getCategoryName } from 'core/utils';
import { ReviewList } from '../Review/Review';
import CategorySelect from '../CategorySelect/CategorySelect';
import ReorderableList from '../ReorderableList/ReorderableList';
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

  componentDidMount() {
    this.props.images.forEach(({ path: src }) => {
      const img = Object.assign(new Image(), { src, onload: () => {
        const canvas = Object.assign(document.createElement('canvas'), {width: img.width, height: img.height});
        canvas.getContext('2d').drawImage(img, 0, 0 );
        this.setState({ [src]: canvas.toDataURL(0, 0, img.width, img.height) });
      }});
    });
  }
  
  static getDerivedStateFromProps({ isEdit, images }, prevState) {
    return (prevState.isEdit !== isEdit) ? ({ isEdit, images }) : null;
  }

  renderField(field, type='text') {
    const [name, value] = Object.entries(field)[0];
    const { isEdit } = this.props;
    if (name === 'category') {
      return isEdit ? <CategorySelect value={value}/> : getCategoryName(value);
    }
    return !isEdit ? value : type === 'textarea' ? <textarea name={name} defaultValue={value}/> : <input name={name} type={type} defaultValue={value}/>;
  }

  onSubmit = e => {
    e.preventDefault();
    const { id } = this.props; 
    addItem({id, data: new FormData(e.target)}).then(res => {
      location.hash = `/_/${id}`; // eslint-disable-line
      console.log(res);
    });
  }
  
  setImages = images => this.setState({images});
  removeImage = item => this.setImages((this.state.images || this.props.images).filter(e => e !== item));

  renderThumbnail = ({ item }) => (
    <div className="item-thumbnail" key={item.id}>
      {this.props.isEdit && <>
        <span className="image-move" title="размести реда на картинките"><FaArrowsAlt /></span>
        <span className="image-remove" title="премахни картинката" onClick={() => this.removeImage(item)} ><FaTimes/></span>
      </>}
      <img className="item-thumbnail-image" src={item.path} alt="" onClick={() => this.setState({ image: item.path })}/>
    </div>
  )
  render() {
    const { author, price, name, category, description, id, isMine, isEdit } = this.props;
    const images = (isEdit && this.state.images) || this.props.images;
    const image = this.state.image || this.props.image;
    const Wrapper = isMine ? 'form' : 'div';

    return (
      <Wrapper className={`Item large ${isEdit ? 'edit' : 'view'}`} id={id} onSubmit={this.onSubmit}>
        <div className="item-name">{this.renderField({name})}</div>
        {isEdit && <>
          <input name="id" value={id} type="hidden"/>
          <input name="images" value={images.map(e => e.id)} type="hidden"/>
        </>}
        {isMine && <Link className="item-edit-link action" to={`/_/${id}${isEdit ? '' : '/edit'}`} ><FaPen title="Редактирай" /></Link>}
        <div className="item-preview">
          <CrossfadeImage className="item-image" src={this.state[image] || image} alt="" />
          <ReorderableList className="item-thumbnails" items={images} setItems={this.setImages} Item={this.renderThumbnail} disabled={!isEdit}/>
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
        {isEdit ? <button className="item-save">Запази</button> : <ReviewList itemId={id} isMine={isMine}/>}
      </Wrapper>
    )
  }
}

export default ItemSimple;

export { ItemSimple, ItemDetailed };