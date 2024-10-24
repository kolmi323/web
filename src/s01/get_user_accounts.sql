SELECT u.*, a.name, a.balance
FROM users as u
JOIN account as a on u.id = a.user_id
WHERE u.id = ?
