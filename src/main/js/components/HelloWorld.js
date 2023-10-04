import React from 'react';

export function HelloWorld(props) {
    return (
        <div className="hello">
            <div className="title">
                Hello {props.name} !
            </div>
            <div className="content">
                Un exemple de composant React.
            </div>
        </div>
    )
}