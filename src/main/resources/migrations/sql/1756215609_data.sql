BEGIN;
SET maintenance_work_mem = '2GB';
SET synchronous_commit = off;
SET work_mem = '256MB';
SET temp_buffers = '256MB';

ALTER TABLE items SET UNLOGGED;

INSERT INTO items (id, name, position)
SELECT
    gen_random_uuid(),
    'name ' || i,
    i * 1024::BIGINT
FROM generate_series(1, 10000000) AS s(i);

ALTER TABLE items SET LOGGED;

COMMIT;
