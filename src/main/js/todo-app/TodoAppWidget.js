import React from 'react';
import { createRoot } from 'react-dom/client';
import { TodoApp } from "./components/TodoApp";

export function mount(props) {
    const container = document.getElementById(props.containerId)
    const root = createRoot(container);
    return root.render(<TodoApp {...props} />);
}
