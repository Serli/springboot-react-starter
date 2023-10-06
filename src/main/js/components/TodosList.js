import React from 'react';
import {Todo} from "./Todo";

export function TodosList({todos, deleteTodo}) {
    return (
        <div className="todos-list">
            {
                todos.map(todo => <Todo todo={todo} deleteTodo={deleteTodo} />)
            }
        </div>
    )
}