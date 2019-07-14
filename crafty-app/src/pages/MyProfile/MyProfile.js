import React, { Component } from 'react';
import { getMyProfile } from 'core/actions';

class MyProfile extends Component {
  state = { data: [] };

  componentDidMount() {
    getMyProfile().then(({data}) => this.setState({data}));
  }
  render() {
    return <pre className="MyProfile">{JSON.stringify(this.state.data, null, 2)}</pre>
  }
}

export default MyProfile;
