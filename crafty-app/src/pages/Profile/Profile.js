import React, { Component, useState } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { FaPen, FaPlus, FaSun, FaMoon } from 'react-icons/fa';
import { getProfile, setProfile } from 'core/actions';
import { ItemSimple } from 'components/Item/Item';
import { getFormData } from 'core/utils';
import './Profile.css';

const DarkToggle = () => {
  const [dark, setDark] = window.store.usePersistentState('darkMode', false);
  return <button className={`dark-toggle ${dark ? 'on' : 'off'}`} aria-pressed={dark} title="включи тъмен режим" onClick={() => setDark(!dark)}>
    <FaSun className="sun"/>
    <FaMoon className="moon"/>
  </button>
}

class Profile extends Component {
  state = { data: { items: [] } };
  loadProfile = () => getProfile(this.state.id).then(({data}) => this.setState({data, loaded: true}));
  getUserId = () => this.props.location.pathname.split('/profile')[1].replace('/edit', '');
  componentDidMount() {
    this.setState({ id: this.getUserId() }, this.loadProfile);
  }
  onSubmit = e => {
    e.preventDefault();
    setProfile(getFormData(e.target)).then(this.loadProfile).then(() => (location.hash = '/profile')); // eslint-disable-line
  }
  componentDidUpdate(prevProps) {
    if (this.props.location.pathname !== prevProps.location.pathname) {
      this.setState({ id:  this.getUserId() }, this.loadProfile);
    }
  }

  render() {
    const { data, loaded, id } = this.state;
    const isMine = !id;
    const isEdit = this.props.location.pathname.endsWith('/edit');
    const Container = isEdit ? 'form' : 'div';

    return (
      <div className={`Profile${isEdit ? ' edit' : ''}`}>
        {isMine && <>
          <Link className="item-add-link action" to="/add" ><FaPlus title="Добави предмет" /></Link>
          <Link className="item-edit-link action" to={`/profile${isEdit ? '' : '/edit'}`} ><FaPen title="Редактирай" /></Link>
          <DarkToggle/>
        </>}
        <Container className="info" onSubmit={this.onSubmit}>
          <div className="name">
            <label>
              <span>Име:</span> {isEdit && <input name="firstName" defaultValue={data.firstName}/>}
            </label>
            {isEdit && <label>
              <span>Фамилия:</span> <input name="lastName" defaultValue={data.lastName}/>
            </label>}
            {!isEdit && `${data.firstName} ${data.lastName}`}
          </div>
          <div className="nick">
            <label><span>Псевдоним:</span> {isEdit && <input name="nickname" defaultValue={data.nickname}/>}</label>
            {!isEdit && data.nickname}
          </div>
          <div className="description">
            <label><span>Описание:</span> {isEdit && loaded && <textarea name="description" defaultValue={data.description}/>}</label>
            {!isEdit && data.description}
          </div>
          {isEdit && <button className="save">Запази</button>}
        </Container>
        <div className="items">
          {data.items.map(props => <ItemSimple key={props.id} {...props} edit={isEdit}/>)}
        </div>
      </div>
    );
  }
}

export default withRouter(Profile);
