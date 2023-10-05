import React from 'react';
import Todo from "../todo/Todo.jsx"
import "./todoList.scss";
export default ({todos})=>{


    return (
        <div class="d-flex flex-column">
            {
                todos && todos.map(todo=>{
                    return <Todo todo={todo} />
                })
            }
        </div>
    )
}

