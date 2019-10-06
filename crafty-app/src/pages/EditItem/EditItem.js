import React, { Component } from 'react';
import { addItem } from 'core/actions';

import './EditItem.css';

// TODO image limit 1mb
// encType
class EditItem extends Component {
  onSubmit = e => {
    e.preventDefault();
    addItem({ url: '/api/v1/items', data: new FormData(e.target)}).then(res => console.log(res));
  }

  renderAdd() {
    return (
      <form className="ItemForm" method="post" action="/api/v1/items" encType="multipart/form-data" onSubmit={e => this.onSubmit(e)}>
        <input name="name" placeholder="Име" required/>
        <input type="file" name="file" multiple />
        <textarea name="description" placeholder="Описание"></textarea>
        <select name="category">
          <option value="EARRINGS">Обеци</option>
        </select>
        <input name="price" type="number" placeholder="Цена"/>
        <input type="submit" value="Добави предмет"/>    
      </form>
    );
  }
  render() {
    if (location.href.endsWith('edit')) { // eslint-disable-line
      return this.renderAdd();
    }
  }
}

export default EditItem;