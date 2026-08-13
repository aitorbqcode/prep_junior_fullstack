-- Get the non completed tasks with the name of the user who have it 

SET search_path TO public;

SELECT t.title, t.completed, u.name, u.email
FROM tasks t
INNER JOIN users u ON t.user_id = u.id
WHERE t.completed = false
ORDER BY u.name

