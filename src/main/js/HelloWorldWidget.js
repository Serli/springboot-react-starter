import React from 'react';
import { createRoot } from 'react-dom/client';
import { HelloWorld } from "./components/HelloWorld";

export function mount(props) {
    const container = document.getElementById(props.containerId)
    const root = createRoot(container);
    return root.render(<HelloWorld {...props} />);
}
