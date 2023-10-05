CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Todo
(
    id         UUID DEFAULT uuid_generate_v4(),
    content    VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE OR REPLACE FUNCTION limit_todos_size() RETURNS trigger AS $$
BEGIN
    DELETE FROM Todo WHERE id NOT IN (SELECT id FROM Todo ORDER BY created_at DESC LIMIT 10);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER limit_todos_size_trigger
    AFTER INSERT ON todo
    EXECUTE FUNCTION limit_todos_size();
