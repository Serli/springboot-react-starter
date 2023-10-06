import React, {useState} from 'react';

export function TodosAddButton({createTodo}) {

    const [content, setContent] = useState("");

    return (
        <div className="todos-add-button">
            <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                <i className="fa-solid fa-plus"></i> Ajouter un TODO
            </button>

            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">Ajouter un TODO</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <form>
                            <div className="modal-body">
                                <input type="text"
                                       className="form-control"
                                       placeholder="Que devez vous faire ?"
                                       name="content"
                                       maxLength="100"
                                       value={content}
                                       onChange={(e) => setContent(e.target.value)} />
                            </div>
                            <div className="modal-footer">
                                <button type="button"
                                        data-bs-dismiss="modal"
                                        className="btn btn-primary"
                                        onClick={() => createTodo(content)}>Ajouter</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}