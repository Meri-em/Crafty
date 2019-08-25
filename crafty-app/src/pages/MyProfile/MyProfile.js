import React, { Component } from 'react';
import { getMyProfile } from 'core/actions';
import { ItemSimple } from 'components/Item/Item';
import './MyProfile.css';

class MyProfile extends Component {
  state = { data: { items: [] } };

  componentDidMount() {
    getMyProfile().then(({data}) => this.setState({data}));
  }
  render() {
    const { data } = this.state;
    return (
      <div className="MyProfile">
        <div className="info">
          <div className="name">Име: {data.firstName} {data.lastName}</div>
          <div className="nick">Ник: {data.nickname}</div>
          <div className="description">{data.description}</div>
        </div>
        <div className="items">
          {data.items.map(props => <ItemSimple key={props.id} {...props}/>)}
        </div>
      </div>
    );
  }
}

export default MyProfile;
