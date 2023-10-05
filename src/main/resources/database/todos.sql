CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Todo
(
    id      UUID DEFAULT uuid_generate_v4(),
    content VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
