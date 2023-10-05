import React from 'react';
import "./todo.scss";

export default ({todo})=>{

    return (
        <div className="d-flex flex-column todo-comp">
            <span>{todo.content}</span>
        </div>
    )
}
