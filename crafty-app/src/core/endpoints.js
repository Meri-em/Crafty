export const SERVER_ROOT = '';
export const API_ROOT    = `${SERVER_ROOT}/api/v1`;

// GENERAL
export const NAVIGATION  = `${API_ROOT}/categories`;

// AUTH
export const REGISTER    = `${API_ROOT}/register`;
export const LOGIN       = `${API_ROOT}/login`;
export const REFRESH     = `${API_ROOT}/refresh`;
export const LOGOUT      = `${API_ROOT}/logout`;
export const PROFILE     = `${API_ROOT}/profile`;

// ITEMS
export const ITEM        = `${API_ROOT}/items/`;
export const ITEMS       = `${API_ROOT}/items/search`;
export const SEARCH      = `${API_ROOT}/items/search`;
export const REVIEWS     = `${API_ROOT}/reviews`;

// CART
export const CART        = `${API_ROOT}/cart`;
export const CART_BUY    = `${API_ROOT}/cart/purchase`;
export const CART_CLEAR  = `${API_ROOT}/cart/clear`;

// ORDERS
export const ORDERS      = `${API_ROOT}/orders`;