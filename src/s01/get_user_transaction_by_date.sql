SELECT users.*, account.name, balance, transaction.to_account_id, amount, date_transaction
FROM users
JOIN account on users.id = account.user_id
JOIN transaction on account.id = transaction.from_account_id
WHERE users.id = ? AND transaction.date_transaction = ?
