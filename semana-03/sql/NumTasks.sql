-- Num of tasks

SET search_path TO public;

SELECT u.name, COUNT(t.id) as TOTAL, SUM(CASE WHEN t.completed THEN 1 ELSE 0 END) as COMPLETED_TASKS,
	SUM(CASE WHEN NOT t.completed THEN 1 ELSE 0 END) as NOT_COMPLETED_TASKS
	FROM users u
	LEFT JOIN tasks t ON t.user_id = u.id
	GROUP BY u.id, u.name
	ORDER BY total DESC;