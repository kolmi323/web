SELECT u.name, SUM(a.balance)
FROM users as u
JOIN account as a on u.id = a.user_id
GROUP BY u.name
