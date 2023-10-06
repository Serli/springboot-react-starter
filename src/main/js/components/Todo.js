import React from 'react';

export function Todo({todo, deleteTodo}) {
    return (
        <div className="card">
            <div className="card-body">
                <p className="card-text">{todo.content}</p>
                <button type="button"
                        className="btn btn-danger"
                        onClick={() => deleteTodo(todo.id)}>
                    <i className="fa-solid fa-trash"></i>
                </button>
            </div>
        </div>
    )
}