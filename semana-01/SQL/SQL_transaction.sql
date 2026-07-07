--Create scheme

CREATE SCHEMA account_sql AUTHORIZATION postgres;

SET search_path TO account_sql;

-- Beggin work
BEGIN WORK;

SET TRANSACTION READ WRITE;

SET datestyle = DMY;

-- create table with the account

CREATE TABLE account (
    user_id VARCHAR(20) PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    balance DECIMAL(9,2) NOT NULL,
    age INTEGER NOT NULL
);

-- Commit work
COMMIT;


--Add rows into the account table
SET search_path TO account_sql;

BEGIN;

INSERT INTO account VALUES('US000000', 'James' ,1000.00, 20);
INSERT INTO account VALUES('US000001', 'Jonathan', 988.67, 25);

COMMIT;

--Check the values are added correctly
SET search_path TO account_sql;

SELECT * FROM account;

--Transaction of the balance in a bank transfer, doing a rollback to check how rollback works
SET search_path TO account_sql;
BEGIN;

UPDATE account SET balance = balance + 30 WHERE user_id = 'US000000';
UPDATE account SET balance = balance - 30 WHERE user_id = 'US000001';

ROLLBACK;

SELECT * FROM account;



