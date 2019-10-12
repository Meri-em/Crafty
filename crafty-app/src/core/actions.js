import axios from 'axios';
import * as EP from './endpoints';
import { LS, toQuery } from './utils';

// GENERAL
export const getNavigation = () => axios(EP.NAVIGATION).then(res => {
  window.store.set('categories', res.data);
  return res;
});

// AUTH
const auth = {
  set: data => {
    window.store.set('isLoggedIn', true);
    return LS.set('tokens', {
      ...data, 
      accessExpires: Date.now() + 20 * 60 * 1000,
      refreshExpires: Date.now() + 4000 * 60 * 1000
    });
  },
  logout: () => {
    window.store.set('isLoggedIn', false);
    LS.set('user', { guest: true });
    return LS.set('tokens', {});
  }
}
window.axios = axios;
export const register = data => axios.post(EP.REGISTER, data).then(setMessage, setError);
export const login    = data => axios.post(EP.LOGIN, data).then(res => {
  auth.set(res.data);
  location.hash = ''; // eslint-disable-line
  getProfile().then(res => LS.set('user', res.data));
}).catch(setError);
export const refresh  = ({ refreshToken }) => axios.post(EP.REFRESH, null, {
  headers: { Authorization: refreshToken }
}).then(res => auth.set(res.data));

export const authorized = settings => {
  const tokens = LS.get('tokens') || {};
  console.log(settings);
  const request = ({ accessToken }) => axios({
    ...settings,
    headers: { ...(settings.headers || {}), Authorization: accessToken }
  });
  if (tokens.accessToken && tokens.accessExpires > Date.now()) {
    console.log('Access active (doing request)');
    return request(tokens);
  }
  if (tokens.refreshToken && tokens.refreshExpires > Date.now()) {
    console.log('Refresh active (refreshing first)');
    return refresh(tokens).then(data => request(data));
  }
  console.log('Tokens expired', tokens);
  LS.set('tokens', {});
  location.hash = '/login';  // eslint-disable-line
  return Promise.reject({});
};

export const logout        = () => authorized({ method: 'POST', url: EP.LOGOUT }).finally(() => auth.logout());
export const getProfile    = id => (id ? axios : authorized)({ url: EP.PROFILE + (id ? '/' + id : '') });
export const setProfile    = data => authorized({ method: 'POST', url: EP.PROFILE, data });


// ITEMS
export const getItem      = id => axios(`${EP.ITEM}${id}`);
export const getReviews   = id => axios(`${EP.REVIEWS}/${id}`);
export const getItems     = () => axios(EP.ITEMS);
export const search       = params => axios(EP.SEARCH + toQuery(params));
export const searchByName = name => search({ text: name });
export const browse       = group => search({ categories: group.toUpperCase() });
// (min|max)-price, text, author-ids, categories
export const addItem      = ({id, data}) => authorized({ method: 'POST', url: `${EP.ITEM}${id || ''}`, data, headers: { 'content-type': 'multipart/form-data' } });
export const deleteItem   = id => authorized({ method: 'PATCH', url: `${EP.ITEM}${id}`, data: { archived: true } });
export const restoreItem  = id => authorized({ method: 'PATCH', url: `${EP.ITEM}${id}`, data: { archived: false } });
export const addReview    = data => authorized({ method: 'POST', url: EP.REVIEWS, data });
export const deleteReview = itemId => authorized({ method: 'DELETE', url: `${EP.REVIEWS}/${itemId}` });

// export const updateDefaultImage = ({itemId, imageId}) => authorized({
//   method: 'POST',
//   url: EP.ITEM + `/${itemId}/images/default`,
//   data: {id: imageId}
// });

// export const deleteItemImage = ({itemId, imageId}) => authorized({
//   method: 'DELETE',
//   url: EP.ITEM + `/${itemId}/images/${imageId}`,
// });

// CART
export const getCart        = () => authorized({ url: EP.CART });
export const clearCart      = () => authorized({ url: EP.CART_CLEAR, method: 'DELETE'}).then(setMessageAndRefresh);
export const buyCart        = () => authorized({ url: EP.CART_BUY, method: 'POST'}).then(setMessageAndRefresh);
export const updateCart     = data => authorized({ url: EP.CART, method: 'POST', data});
export const addToCart      = ({id, quantity=1}) => updateCart({itemId: id, quantity: `+${quantity}`}).then(setMessage);
export const removeFromCart = ({id, quantity=1}) => updateCart({itemId: id, quantity: `-${quantity}`});

// ORDERS
export const getOrders      = () => authorized({ url: EP.ORDERS });

export const setMessage = res => window.store.set('message', res.data || res);
export const setError = ({response}) => setMessage(response);
export const clearMessage = () => window.store.set('message', '');
export const setMessageAndRefresh = res => (window.store.set('message', res.data || res), location.reload()); // eslint-disable-line
