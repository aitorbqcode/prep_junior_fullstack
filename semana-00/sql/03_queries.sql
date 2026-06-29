-- QUERIES

-- GET THE PRODUCT_ID ORDERED BY THE USER US000001. 
SET search_path TO user_orders;

SELECT p.product_id
FROM PRODUCTS p
JOIN ORDER_PRODUCT op ON p.product_id = op.product_id
JOIN ORDERS o ON op.order_id = o.order_id
WHERE o.user_id = 'US000001';

--GET THE ORDER_ID AND THE NUM OF ORDERS FROM THE ORDER WITH 2 DIFERENT PRODUCTS. ORDER BY THE ORDER_ID

SELECT o.order_id, COUNT(o.order_id) AS total_products
FROM ORDERS o
JOIN ORDER_PRODUCT op ON op.order_id = o.order_id
JOIN PRODUCTS p ON  op.product_id = p.product_id
GROUP BY o.order_id
HAVING COUNT(o.order_id) = 2
ORDER BY o.order_id;