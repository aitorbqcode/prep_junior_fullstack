-- Users without tasks

SET search_path TO public;

SELECT u.name, u.email
   FROM users u
   LEFT JOIN tasks t ON t.user_id = u.id
   WHERE t.id IS NULL;
