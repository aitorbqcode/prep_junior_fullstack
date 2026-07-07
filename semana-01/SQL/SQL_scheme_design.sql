-- Normalización en la práctica (no solo la teoría): Dado este diseño desnormalizado, llévalo hasta 3FN:

-- Pedidos(id_pedido, cliente_nombre, cliente_email, cliente_ciudad, producto_nombre, producto_precio, cantidad)

-- ¿Cuántas tablas necesitas? ¿Qué claves foráneas aparecen?

--Create scheme

CREATE SCHEMA order_sql AUTHORIZATION postgres;

SET search_path TO order_sql;

-- Beggin work
BEGIN WORK;

SET TRANSACTION READ WRITE;

SET datestyle = DMY;

-- create table with the account

CREATE TABLE CLIENT (
	client_id VARCHAR(20) PRIMARY KEY,
	client_name VARCHAR(30) NOT NULL,
	client_city VARCHAR(20) NOT NULL,
	client_email VARCHAR(50) UNIQUE,
	CONSTRAINT CHK_CLIENT_CITY CHECK (client_city IN ('Barcelona', 'Madrid', 'Valencia'))
);

CREATE TABLE PRODUCT (
	product_id VARCHAR(20) PRIMARY KEY,
	product_name VARCHAR(20) NOT NULL,
	price DECIMAL(9,2) DEFAULT 0.0
);

CREATE TABLE ORDERS (
    order_id VARCHAR(20) PRIMARY KEY,
    client_id VARCHAR(20) NOT NULL,
    price DECIMAL(9,2) DEFAULT 0.0,
	CONSTRAINT FK_CLIENT_ID_ORDER FOREIGN KEY (client_id) REFERENCES CLIENT(client_id)
);

CREATE TABLE ORDER_LINE (
	order_id VARCHAR(20) NOT NULL,
	product_id VARCHAR(20) NOT NULL,
	unit_price DECIMAL(9,2) DEFAULT 0.0,
	CONSTRAINT PK_ORDER_LINE PRIMARY KEY (order_id, product_id),
	CONSTRAINT FK_ORDER_ID_ORDER_LINE FOREIGN KEY (order_id) REFERENCES ORDERS(order_id),
	CONSTRAINT FK_PRODUCT_ID_ORDER_LINE FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
);

