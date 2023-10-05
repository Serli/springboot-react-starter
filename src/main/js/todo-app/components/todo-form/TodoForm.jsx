import React, {useState} from 'react';

import "./todoForm.scss";
export default ({addCallback})=>{

    const [label, setLabel] = useState("");
    const [description, setDescription] = useState("");

    const createTodo=()=>{
        addCallback({label, description});
        setLabel("");
        setDescription("");
    }

    return (
        <div className="d-flex flex-column todo-form">
            <input id="label" placeholder="Titre"
                   className="form-control mb-2"
                   maxLength={100}
                   value={label} onChange={(e)=>setLabel(e.currentTarget.value)}/>
                <textarea placeholder="Description"
                          className="form-control mb-2"
                          value={description}
                          maxLength={100}
                          onChange={(e)=>setDescription(e.currentTarget.value)}></textarea>
            <button type="button" className="btn btn-success" onClick={createTodo}>Créer</button>
        </div>
    )
}
