import axios from 'axios';
import * as EP from './endpoints';
import { authorized, toQuery } from './utils';

// TEST
export const welcome  = () => axios(EP.WELCOME);

// AUTH
export const register = data => axios.post(EP.REGISTER, data);
export const login    = data => axios.post(EP.LOGIN, data);
export const refresh  = ({ refreshToken }) => axios.post({
  url: EP.REFRESH,
  headers: { Authorization: refreshToken }
});
export const logout        = authorized({ method: 'POST', url: EP.LOGOUT });
export const getMyProfile  = authorized({ url: EP.MY_PROFILE });

// ITEMS
export const getItem   = id => axios(`${EP.ITEM}${id}`);
export const getItems  = () => axios(EP.ITEMS);
export const search    = params => axios(EP.SEARCH + toQuery(params));