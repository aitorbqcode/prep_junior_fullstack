CREATE SCHEMA test_sql AUTHORIZATION postgres;

SET search_path TO test_sql;

BEGIN WORK;

SET TRANSACTION READ WRITE;

SET datestyle = DMY;

-- create tables

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    ciudad VARCHAR(50) NOT NULL,
    edad INTEGER NOT NULL
);

COMMIT;

-- Insert 10.000 rows in the table
INSERT INTO usuarios (nombre, email, ciudad, edad)
SELECT
    'Usuario_' || i,
    'usuario_' || i || '@gmail.com',
    (ARRAY['Madrid', 'Barcelona', 'Valencia', 'Sevilla', 'Bilbao'])[1 + (i % 5)],
    18 + (i % 50)
FROM generate_series(1, 10000) AS i;


--Check it added 10000 new users
SET search_path TO test_sql;

SELECT * FROM usuarios;


--Check the cost, check
EXPLAIN ANALYZE SELECT * FROM usuarios WHERE email = 'usuario_5000@gmail.com';


--If we create an index the time get reduced:

CREATE INDEX idx_usuarios_email ON usuarios(email);

EXPLAIN ANALYZE SELECT * FROM usuarios WHERE email = 'usuario_5000@gmail.com';