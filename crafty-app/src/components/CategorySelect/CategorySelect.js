import React from 'react';

const CategorySelect = ({ value }) => (
  <select name="category" defaultValue={value}>
    {window.store.get('categories', []).map(g => (
      <optgroup label={g.text} key={g.href}>
        {g.items.map(e => <option value={e.href} key={e.href}>{e.text}</option>)}
      </optgroup>
    ))}
  </select>
);

export default CategorySelect;