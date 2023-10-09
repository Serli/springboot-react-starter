CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE TodoStatus AS ENUM ('NEW', 'IN_PROGRESS', 'DONE');

CREATE TABLE Todo
(
    id         UUID                     DEFAULT uuid_generate_v4(),
    title      VARCHAR(100) NOT NULL,
    content    VARCHAR(500) NOT NULL,
    status     TodoStatus               DEFAULT 'NEW',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    PRIMARY KEY (id)
);

-- Création d'un Trigger permettant de limiter le nombre de ligne dans la table des Todos.
-- Ce trigger est particulièrement utile en mode déployé sur Clever, vu que l'accès est public, pour éviter de se faire remplir la base.
CREATE OR REPLACE FUNCTION limit_todos_size() RETURNS trigger AS $$
BEGIN
    DELETE FROM Todo WHERE id NOT IN (SELECT id FROM Todo ORDER BY created_at DESC LIMIT 10);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER limit_todos_size_trigger
    AFTER INSERT ON todo
    EXECUTE FUNCTION limit_todos_size();
