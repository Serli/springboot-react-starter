import React, {useEffect, useState} from 'react';

import {TodosAddButton} from "./TodosAddButton";
import {TodosList} from "./TodosList";

export function Todos(props) {

    const [todos, setTodos] = useState([])

    const loadTodos = () => {
        fetch('/api/v1/todos')
            .then(res => res.json())
            .then(json => setTodos(json))
            .catch(err => console.error(err))
    }

    const createTodo = (content) => {
        fetch('/api/v1/todos', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({content})
        })
            .then(res => res.json())
            .then(json => {
                console.log(`Todo created! ${json}`);
                loadTodos();
            })
    }

    const deleteTodo = (id) => {
        fetch(`/api/v1/todos/${id}`, {
            method: "DELETE"
        })
            .then(res => res.json())
            .then(json => {
                console.log(`Todo deleted! ${json}`);
                loadTodos();
            })
    }

    useEffect(() => {
        loadTodos()
    }, [])

    return (
        <div className="todos">
            <TodosAddButton createTodo={createTodo}/>
            <TodosList todos={todos} deleteTodo={deleteTodo}/>
        </div>
    )
}