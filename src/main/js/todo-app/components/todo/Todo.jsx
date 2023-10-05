import React from 'react';
import "./todo.scss";

export default ({todo}) => {

    const getStatus = () => {
        switch (todo.status) {
            case 2:
                return "Terminé";
            case 1:
                return "En cours";
            case 0:
            default:
                return "En attente";
        }
    }
    return (
        <fieldset className="d-flex flex-column todo-comp">
            <legend>{todo.title}</legend>
            <span>{todo.content}</span>
            <span className="date">{Intl.DateTimeFormat().format(new Date(todo.createdAt))}</span>
            <span className="status">{getStatus()}</span>
        </fieldset>
    )
}
