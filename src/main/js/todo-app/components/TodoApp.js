import React, {useEffect, useState} from 'react';

import TaskList from "./todo-list/TodoList.jsx";
import TodoForm from "./todo-form/TodoForm.jsx";
import TodoFilter from "./todo-filter/TodoFilter.jsx";
import TodoStats from "./todo-stats/TodoStats.jsx";

import "./todoApp.scss";

export function TodoApp(props) {
    const [todos, setTodos] = useState([]);
    const [filter, setFilter] = useState({status:[0, 1,2], dateDebut:undefined, dateFin:undefined});

    const loadTodos = () => {
        fetch("/api/v1/todos")
            .then(data => data.json())
            .then(setTodos)
            .catch(console.error)
    }

    const filterChanged = (f)=>{
        console.log("setting filters", f)
        setFilter({...filter, ...f});
    }


    useEffect(loadTodos, []);

    useEffect(() => {
        console.log('useEffect => ', filter);
    }, [filter])

    const addTodos = (todo) => {
        fetch("/api/v1/todos", {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                content: todo.description,
                title: todo.label,
                status: 1
            })
        }).then(loadTodos)
    }

    const todosToShow = todos.filter(todo=>{
        let inStatus = filter.status.indexOf(todo.status) >= 0;
        let afterDateDebut = !filter.dateDebut || todo.createdAt >= filter.dateDebut;
        let beforeDateFin = !filter.dateFin || todo.createdAt <= filter.dateFin;
        return inStatus && afterDateDebut && beforeDateFin
    })

    return (
        <div className="todos-container">
            <div id="todo-form" className="area">
                <fieldset>
                    <legend>Nouvelle Tâche</legend>
                    <TodoForm addCallback={addTodos}></TodoForm>
                </fieldset>
            </div>
            <div id="todo-filter" className="area">
                <fieldset>
                    <legend>Filtres</legend>
                    <TodoFilter filterCallback={filterChanged}></TodoFilter>
                </fieldset>
            </div>
            <div id="todo-chart" className="area">
                <fieldset>
                    <legend>Statistiques</legend>
                    <TodoStats />
                </fieldset>
            </div>
            <div id="todo-list" className="area">
                <fieldset>
                    <legend>Liste des tâches</legend>
                    <TaskList todos={todosToShow}/>
                </fieldset>
            </div>
        </div>
    )
}
