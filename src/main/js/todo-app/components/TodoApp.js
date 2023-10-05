import React, {useEffect, useState} from 'react';

import TaskList from "./todo-list/TodoList.jsx";
import TodoForm from "./todo-form/TodoForm.jsx";

import "./todoApp.scss";

export function TodoApp(props) {
    const [todos, setTodos] = useState([]);

    const loadTodos = () => {
        fetch("/api/v1/todos")
            .then(data => data.json())
            .then(setTodos)
            .catch(console.error)
    }

    useEffect(loadTodos, [])

    const addTodos = (todo) => {
        fetch("/api/v1/todos", {
            method: "POST",
            headers:{
                "Content-type":"application/json"
            },
            body: JSON.stringify({
                content: todo.description
            })
        }).then(loadTodos)
    }

    return (
        <div className="todos-container">
            <div id="todo-form">
                <fieldset>
                    <legend>Nouvelle Tâche</legend>
                    <TodoForm addCallback={addTodos}></TodoForm>
                </fieldset>
            </div>
            <div id="todo-filter">

            </div>
            <div id="todo-chart"></div>
            <div id="todo-list">
                <fieldset>
                    <legend>Liste des tâches</legend>
                    <TaskList todos={todos}/>
                </fieldset>
            </div>
        </div>
    )
}
