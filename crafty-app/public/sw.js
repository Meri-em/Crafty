const offlineCache = 'offline-v1';
const offlineFiles = ['/', '/index.html'];

const strategies = {
  cacheOrNetwork: req => caches.match(req).then(res => res || fetch(req)),
  networkOrCache: req => fetch(req).catch(_ => caches.match(req)),
};

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(offlineCache).then(cache => cache.addAll(offlineFiles))
  );
});

self.addEventListener('fetch', event => {
  event.respondWith(strategies.networkOrCache(event.request));
});