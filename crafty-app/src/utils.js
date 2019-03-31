import axios from 'axios';

export const authorized = settings => ({ accessToken }) => axios({
  ...settings,
  headers: { Authorization: accessToken }
});

export const toQuery = obj => '?' + Object.entries(obj).map(
  ([ k, v ]) => (Array.isArray(v) ? v : [v]).map(e => `${k}=${v}`).join('&')
).join('&');