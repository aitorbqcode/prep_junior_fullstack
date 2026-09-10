-- Vista: resumen de tareas por usuario
CREATE VIEW user_task_summary AS
SELECT
    u.id AS user_id,
    u.name,
    u.email,
    COUNT(t.id) AS total_tasks,
    SUM(CASE WHEN t.completed THEN 1 ELSE 0 END) AS completed_tasks,
    SUM(CASE WHEN NOT t.completed THEN 1 ELSE 0 END) AS pending_tasks,
    ROUND(
        100.0 * SUM(CASE WHEN t.completed THEN 1 ELSE 0 END) / NULLIF(COUNT(t.id), 0),
        1
    ) AS completion_rate
FROM users u
LEFT JOIN tasks t ON t.user_id = u.id
GROUP BY u.id, u.name, u.email;

-- Úsala como si fuera una tabla:
SELECT * FROM user_task_summary WHERE completion_rate > 50;

-- 1. Asegurar que la tabla tasks tiene un campo de fecha (opcional si no lo tenías)
ALTER TABLE tasks ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 2. Crear la vista recent_tasks
CREATE OR REPLACE VIEW recent_tasks AS
SELECT 
    t.id AS task_id,
    t.title,
    t.completed,
    t.created_at,
    u.id AS user_id,
    u.name AS user_name,
    u.email AS user_email
FROM tasks t
JOIN users u ON t.user_id = u.id
WHERE t.created_at >= NOW() - INTERVAL '7 days';


-- Comprobaciones
-- Probar la vista general de métricas
SELECT * FROM user_task_summary;

-- Probar la vista de tareas recientes
SELECT * FROM recent_tasks;

-- Ranking de usuarios con funciones de ventana
SELECT 
    u.name,
    COUNT(t.id) AS completed,
    DENSE_RANK() OVER (ORDER BY COUNT(t.id) DESC) AS ranking
FROM users u
JOIN tasks t ON t.user_id = u.id AND t.completed = true
GROUP BY u.id, u.name;


SELECT current_database(), current_schema();