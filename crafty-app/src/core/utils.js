export const LS = window.LS = {
  prefix: 'sky',
  version: 1,
  key(key){
    return `${this.prefix}[${this.version}][${key}]`;
  },
  set(key, value) {
    localStorage.setItem(this.key(key), JSON.stringify(value));
    return value;
  },
  get(key) {
    return JSON.parse(localStorage.getItem(this.key(key)) || 'null');
  }
};

export const toQuery = obj => '?' + Object.entries(obj).map(
  ([ k, v ]) => (Array.isArray(v) ? v : [v]).map(e => `${k}=${v}`).join('&')
).join('&');

export const isLoggedIn = () => (LS.get('tokens') || {}).refreshExpires > Date.now();

export const getUser = () => isLoggedIn() && LS.get('user') || { guest: true }; // eslint-disable-line

export const getFormData = ({ elements }) => [].reduce.call(elements, (data, { name, value }) => {
  const [group, key] = name.match(/(\w+)\.(\w+)/) || [];
  if (group) {
    data[group] = Object.assign(data[group] || {}, { [key]: value })
  } else if (name) {
    data[name] = value;
  }
  return data;
}, {});