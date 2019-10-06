import React, { Component } from 'react';
import { addItem } from 'core/actions';
import CategorySelect from 'components/CategorySelect/CategorySelect';

import './AddItem.css';

// TODO image limit 1mb
class AddItem extends Component {
  onSubmit = e => {
    e.preventDefault();
    addItem({data: new FormData(e.target)}).then(res => {
      location.hash = `/profile`; // eslint-disable-line
      console.log(res);
    });
  }
  render() {
    return (
      <form className="ItemForm" method="post" action="/api/v1/items" encType="multipart/form-data" onSubmit={e => this.onSubmit(e)}>
        <h1>Добави нов предмет</h1>
        <input name="name" placeholder="Име" required/>
        <input type="file" name="file" multiple/>
        <textarea name="description" placeholder="Описание"></textarea>
        <CategorySelect />
        <input name="price" type="number" placeholder="Цена"/>
        <input type="submit" value="Добави предмет"/>    
      </form>
    );
  }
}

export default AddItem;