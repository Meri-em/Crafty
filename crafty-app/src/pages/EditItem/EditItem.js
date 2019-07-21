import React, { Component } from 'react';


class EditItem extends Component {
  render() {
    return (
      <form method="post" action="http://localhost:8080/api/v1/upload" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" value="Upload Image"/>    
      </form>
    );
  }
}

export default EditItem;