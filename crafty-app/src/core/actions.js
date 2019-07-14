import axios from 'axios';
import * as EP from './endpoints';
import { LS, toQuery, transform } from './utils';

// GENERAL
export const welcome       = () => axios(EP.WELCOME);
export const getNavigation = () => axios(EP.NAVIGATION);

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
    return LS.set('tokens', {});
  }
}
export const register = data => axios.post(EP.REGISTER, data);
export const login    = data => axios.post(EP.LOGIN, data).then(res => auth.set(res.data));
export const refresh  = ({ refreshToken }) => axios.post(EP.REFRESH, null, {
  headers: { Authorization: refreshToken }
}).then(res => auth.set(res.data));

export const authorized = settings => {
  const tokens = LS.get('tokens') || {};
  console.log(settings);
  const request = ({ accessToken }) => axios({
    ...settings,
    headers: { Authorization: accessToken }
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
  return Promise.reject({})
};

export const logout        = () => authorized({ method: 'POST', url: EP.LOGOUT }).finally(() => auth.logout());
export const getMyProfile  = () => authorized({ url: EP.MY_PROFILE });


const items = axios.create({ transformResponse: data => transform(JSON.parse(data)) });

// ITEMS
export const getItem      = id => items(`${EP.ITEM}${id}`);
export const getItems     = () => items(EP.ITEMS);
export const search       = params => items(EP.SEARCH + toQuery(params));
export const searchByName = name => search({ text: name });
export const browse       = group => search({ categories: group.toUpperCase() });
// (min|max)-price, text, author-ids, categories


// CART
export const getCart        = () => authorized({ url: EP.CART });
export const updateCart     = data => authorized({ url: EP.CART, method: 'POST', data});
export const addToCart      = ({id, quantity=1}) => updateCart({itemId: id, quantity: `+${quantity}`});
export const removeFromCart = ({id, quantity=1}) => updateCart({itemId: id, quantity: `-${quantity}`});
