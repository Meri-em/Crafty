export const LS = {
  prefix: 'sky',
  version: 1,
  key(key){
    return `${this.prefix}[${this.version}][${key}]`;
  },
  set(key, value) {
    return localStorage.setItem(this.key(key), JSON.stringify(value));
  },
  get(key) {
    return JSON.parse(localStorage.getItem(this.key(key)) || 'null');
  }
};

export const toQuery = obj => '?' + Object.entries(obj).map(
  ([ k, v ]) => (Array.isArray(v) ? v : [v]).map(e => `${k}=${v}`).join('&')
).join('&');