import axios from 'axios';
import * as EP from './endpoints';
import { LS, toQuery, transform } from './utils';

// TEST
export const welcome  = () => axios(EP.WELCOME);

// AUTH
export const register = data => axios.post(EP.REGISTER, data);
export const login    = data => axios.post(EP.LOGIN, data).then(data => LS.set('tokens', data));
export const refresh  = ({ refreshToken }) => axios.post({
  url: EP.REFRESH,
  headers: { Authorization: refreshToken }
}).then(data => LS.set('tokens', data));

export const authorized = settings => {
  const tokens = LS.get('tokens') || {};
  const request = ({ accessToken }) => axios({
    ...settings,
    headers: { Authorization: accessToken }
  });
  if (tokens.accessToken && tokens.expires < Date.now()) {
    return request(tokens);
  }
  if (tokens.refreshToken) {
    return refresh(tokens).then(request);
  }
  return () => Promise.reject({})
};

export const logout        = authorized({ method: 'POST', url: EP.LOGOUT });
export const getMyProfile  = authorized({ url: EP.MY_PROFILE });


const items = axios.create({ transformResponse: data => transform(JSON.parse(data)) });

// ITEMS
export const getItem   = id => items(`${EP.ITEM}${id}`);
export const getItems  = () => items(EP.ITEMS);
export const search    = params => items(EP.SEARCH + toQuery(params));
export const browse    = group => search({ categories: group.toUpperCase() });
// (min|max)-price, text, author-ids, categories