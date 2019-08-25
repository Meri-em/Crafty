import React, { Component, useState } from 'react';
import { Link } from 'react-router-dom';
import { addReview, getReviews, deleteReview } from 'core/actions';
import { FaPen, FaTrash } from 'react-icons/fa';
import './Review.css';
import { getUser, getFormData } from 'core/utils';

const EditReview = ({ itemId, comment, score, setEdit, addItem }) => (
  <form className="EditReview" onSubmit={e => {
    e.preventDefault();
    setEdit(false);
    addItem(getFormData(e.target));
  }}>
    <input name="itemId" type="hidden" value={itemId} />
    <select name="score" className="score" defaultValue={score >> 0 || 5}>{[1,2,3,4,5].map(e => <option key={e} value={e}>{e} ★</option>)}</select>
    <textarea name="comment" className="comment" placeholder="Добави ревю" defaultValue={comment} />
    <button type="button" className="cancel" onClick={() => setEdit(false)}>Отказ</button>
    <button type="submit" className="publish">Публикувай</button>
  </form>
);

const Review = ({ itemId, member, comment, score, lastUpdated, editable, addItem, deleteItem }) => {
  const [edit, setEdit] = useState(false);
  if (edit || !lastUpdated) {
    return <EditReview comment={comment} score={score} itemId={itemId} setEdit={setEdit} addItem={addItem}/>;
  }
  return (
    <div className="Review" key={member.id}>
      <Link className="author" to={`/user/${member.id}`}>{member.name}</Link>
      <span className="last-edit">Последна промяна: {lastUpdated.split('-').reverse().join('.')}</span>
      <div className="score">
        {[...Array(5).keys()].map(i => <span key={i} className={`star ${i < score ? '': 'in'}active`}>{i < score ? '★' : '☆'}</span>)}
      </div>
      {editable && <div className="actions">
        <div className="action delete" onClick={deleteItem}><FaTrash title="Премахни"/></div>
        <div className="action edit" onClick={() => setEdit(!edit)}><FaPen title="Редактирай"/></div>
      </div>}
      <div className="comment">{comment}</div>
    </div>
  )
};
export class ReviewList extends Component {
  state = { reviews: [], user: getUser() };

  updateList = () => getReviews(this.props.itemId).then(res => this.setState({ reviews: res.data }));
  addItem = data => addReview(data).then(this.updateList);
  deleteItem = () => deleteReview(this.props.itemId).then(this.updateList);

  componentDidMount() {
    this.updateList();
  }
  render() {
    const { reviews, user } = this.state;
    const mine = reviews.find(r => r.member.id === user.id) || { member: { id: user.id } };
    const others = reviews.filter(r => r !== mine);
  
    return (
      <div className="reviews">
        <Review {...mine} editable={true} itemId={this.props.itemId} addItem={this.addItem} deleteItem={this.deleteItem} />
        {others.map(r => <Review {...r}/>)}
      </div>
    );
  }
}
export default Review;