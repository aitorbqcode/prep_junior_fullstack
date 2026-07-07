¿Qué es un índice internamente?

Sirve para realizar una estructura interna de indices en nuestras tabla de SQL, de esta forma los procesos de busqueda
tienen un coste de  (log n) lo que quiere decir que en cada iteración descarta a la mitad

Crea una tabla con 10.000+ filas (puedes generarlas con una función o importar un dataset).

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

Ejecuta una query de búsqueda sin índice y con EXPLAIN ANALYZE mira cuánto tarda.

EXPLAIN ANALYZE SELECT * FROM usuarios WHERE email = 'test@gmail.com';

Seq Scan on usuarios  (cost=0.00..180.00 rows=1 width=50)
(actual time=0.543..2.341 rows=1 loops=1)


Crea un índice sobre la columna que filtras y vuelve a ejecutar EXPLAIN ANALYZE.
Documenta la diferencia en /semana1/jueves/indices.md.

Sin índice: Seq Scan, 9999 filas descartadas, 0.929 ms
Con índice: Index Scan, 0 filas descartadas, 0.056 ms

Mucho más rápido.

Pregunta clave que debes saber responder: ¿cuándo un índice puede empeorar el rendimiento?
(Pista: operaciones de escritura)

Puede empeorar en operaciones de escritura, ya que en cada una de las lineas hay que añadir este extra
de informacion, aunque luego en los procesos de lectura, logramos encontrar la información mucho mas rapido.
En tablas pequeñas, donde hay pocas dilas el Seq Scan es mas rapido que con el indice, indices que nadie consulta
lo que añade un peso extra de memoria o queries que devuelven muchas filas si hay que leer el ndice mas la tabla es 
mas lento