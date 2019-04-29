import React, { Component } from 'react';


class EditItem extends Component {
  render() {
    return (
      <form action="http://localhost:8080/api/v1/upload">
        <input type="file"/>
        <input type="submit" value="Upload Image"/>    
      </form>
    );
  }
}

export default EditItem;