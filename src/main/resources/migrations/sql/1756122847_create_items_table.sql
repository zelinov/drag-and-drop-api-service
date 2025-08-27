CREATE TABLE items
(
    id       UUID         NOT NULL PRIMARY KEY,
    version  SERIAL       NOT NULL,
    name     VARCHAR(100) NOT NULL,
    position BIGINT       NOT NULL,
    UNIQUE (position) DEFERRABLE INITIALLY DEFERRED
);