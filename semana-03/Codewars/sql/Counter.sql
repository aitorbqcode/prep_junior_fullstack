-- # write your SQL statement here: 
-- you are given a table 'counter' with columns 'x' (int) and 'n' (int)
-- return a query with columns 'x', 'n' and your result in a column named 'res' (array)
-- sort results by column 'x' ascending, then by 'n' ascending
-- note that each pair of 'x' and 'n' in 'counter' is unique 

-- CREATE TABLE

SET search_path to test;

BEGIN WORK;

SET TRANSACTION READ WRITE;

SET datestyle = DMY;

CREATE TABLE counter (
	x INTEGER PRIMARY KEY,
	n INTEGER NOT NULL
);

COMMIT WORK;

-- INSERT VALUES

SET search_path to test;

BEGIN WORK;

INSERT INTO counter VALUES(1, 10);
INSERT INTO counter VALUES(2, 5);
INSERT INTO counter VALUES(3, 7);
INSERT INTO counter VALUES(50, 5);
INSERT INTO counter VALUES(100, 6);

COMMIT WORK;

-- COUNT
SET search_path to test;

SELECT x, n, ARRAY( SELECT generate_series(1, n) * x) AS res
FROM counter
ORDER BY x ASC, n ASC;


-- CHECK

SET search_path to test;

SELECT * FROM counter
ORDER BY x DESC;