import React from 'react';
import { connect } from 'core/store';
import { clearMessage } from 'core/actions';

import './Notification.css';

const Notification = ({message=''}) => message && (
  <div className={`Notification ${message.error ? 'error' : 'info'}`}>
    <div className="message">{message.error || message}</div>
    <div className="close" onClick={clearMessage}>&times;</div>
  </div>
);

export default connect(({message}) => ({message}))(Notification);