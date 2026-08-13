-- # write your SQL statement here: you are given a table 'solution'
-- with column 'str', return a table with column 'str'
-- and your result in a column named 'res'.
-- order the result by the column 'str', in ascending order

-- CREATE TABLE
SET search_path TO test;

BEGIN WORK;

SET TRANSACTION READ WRITE;

SET datestyle = DMY;

CREATE TABLE solutions (
	str VARCHAR(50) PRIMARY KEY
);

COMMIT WORK;

-- INSERT VALUES

BEGIN WORK;

SET search_path TO test;

INSERT INTO solutions VALUES('world');
INSERT INTO solutions VALUES('hello');
INSERT INTO solutions VALUES('');
INSERT INTO solutions VALUES('h');

COMMIT WORK;

-- REVERSE STR VALUES TO THE

SET search_path TO test;

SELECT str, REVERSE(str) AS res
FROM solutions
ORDER BY str ASC;