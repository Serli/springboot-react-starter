import React from 'react';
import Todo from "../todo/Todo.jsx"
import "./todoList.scss";
export default ({todos})=>{


    return (
        <div class="d-flex todo-list">
            {
                todos && todos.map(todo=>{
                    return <Todo todo={todo} />
                })
            }
        </div>
    )
}

