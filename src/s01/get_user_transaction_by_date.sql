SELECT u.*, a.name, a.balance, t.from_account_id, t.to_account_id, t.amount, t.date
FROM users as u
JOIN account as a on u.id = a.user_id
JOIN transaction as t on a.id = t.from_account_id OR a.id = t.to_account_id
WHERE u.id = ? AND t.date = CURRENT_DATE - 1
