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

const remap = Object.entries({
  primaryImagePath: 'image',
  itemImages: 'images',
});

export const transform = data => {
  if (Array.isArray(data)) return data.map(transform);

  remap.forEach(([theirs, ours]) => {
    if (data[theirs]) {
      data[ours] = data[theirs];
      delete data[theirs];
    }
  });
  
  return data;
};

export const isLoggedIn = () => (LS.get('tokens') || {}).refreshExpires > Date.now();