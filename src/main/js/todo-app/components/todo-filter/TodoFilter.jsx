import React, {useState} from 'react';


export default ({filterCallback}) => {

    const [statutEnAttente, setStatutEnAttente] = useState(true);
    const [statutEnCours, setStatutEnCours] = useState(true);
    const [statutTermine, setStatutTermine] = useState(true);
    const [dateDebut, setDateDebut] = useState(undefined);

    const applyFilter = () => {
        let status = [];
        statutEnCours && status.push(1);
        statutTermine && status.push(2);
        filterCallback({status})
    }


    const updateStatutEnCours = (statut) => {
        let status = [];
        setStatutEnCours(statut);
        statut && status.push(1);
        statutEnAttente && status.push(0);
        statutTermine && status.push(2);
        filterCallback({status})
    }

    const updateStatutEnAttente = (statut) => {
        let status = [];
        setStatutEnAttente(statut);
        statut && status.push(0);
        statutTermine && status.push(2);
        statutEnCours && status.push(1);
        filterCallback({status})
    }

    const updateStatutTermine = (statut) => {
        let status = [];
        setStatutTermine(statut);
        statut && status.push(2);
        statutEnCours && status.push(1);
        statutEnAttente && status.push(0);
        filterCallback({status})
    }

    const updateDateDebut=(e)=>{
        let status = [];
        statutTermine && status.push(2);
        statutEnCours && status.push(1);
        statutEnAttente && status.push(0);
        let dateDebut = new Date(e.currentTarget.value).getTime();
        setDateDebut(e.currentTarget.value);
        filterCallback({status, dateDebut})
    }

    return (
        <div className="d-flex todo-form">
            <fieldset>
                <legend>État</legend>
                <label>
                    <input id="statutEnAttente"
                           type="checkbox"
                           className="mr-2"
                           checked={statutEnAttente} onChange={(e) => {
                        updateStatutEnAttente(!statutEnAttente);
                    }}/>
                    En attente</label>
                <label>
                    <input id="statutEnCours"
                           type="checkbox"
                           className="mr-2"
                           checked={statutEnCours} onChange={(e) => {
                        updateStatutEnCours(!statutEnCours);
                    }}/>
                    En cours</label>
                <label>
                    <input id="statutTermine"
                           type="checkbox"
                           className="mr-2"
                           checked={statutTermine} onChange={(e) => {
                        updateStatutTermine(!statutTermine);
                    }}/>
                    Terminé</label>
            </fieldset>
                <fieldset>
                    <legend>Date</legend>
                    <input type="date" value={dateDebut} onChange={updateDateDebut}/>
                </fieldset>
        </div>
    )
}
