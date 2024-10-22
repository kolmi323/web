SELECT users.*, account.name, balance
FROM users
JOIN account ON users.id = account.user_id
WHERE users.id = ?
