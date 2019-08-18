import React from 'react';
import { connect } from 'core/store';
import { logout } from 'core/actions';

// import './Header.css';

const Notification = ({message}) => (
  <div className="Notification">
    <div className="message">{message}</div>
    <div className="close">&times;</div>
  </div>
);

export default connect(({message}) => ({message}))(Notification);