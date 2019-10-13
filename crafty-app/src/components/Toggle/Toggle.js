import React, { useState } from 'react';

const Toggle = ({ className, children }) => {
  const [open, setOpen] = useState(false);
  return <button className={`${className} menu-toggle ${open ? 'on' : 'off'}`} aria-expanded={open} aria-haspopup="true" onClick={() => setOpen(!open)}>{children}</button>
}

export default Toggle;