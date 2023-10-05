CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Todo
(
    id      UUID DEFAULT uuid_generate_v4(),
    content TEXT NOT NULL,
    PRIMARY KEY (id)
);
