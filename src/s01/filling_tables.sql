INSERT INTO users (name, email, password)
VALUES ('John Doe', 'john.doe@example.com', 'pass1234'),
       ('Jane Smith', 'jane.smith@example.com', 'pass5678'),
       ('Mike Johnson', 'mike.johnson@example.com', 'pass9101'),
       ('Alice Williams', 'alice.williams@example.com', 'pass1121'),
       ('Bob Brown', 'bob.brown@example.com', 'pass1314');

INSERT INTO account (user_id, name, balance)
VALUES (1, 'John Savings Account', 5000.00),
       (1, 'John Checking Account', 3000.00),
       (2, 'Jane Savings Account', 6000.00),
       (3, 'Mike Checking Account', 1000.00),
       (4, 'Alice Investment Account', 15000.00),
       (4, 'Alice Savings Account', 12000.00),
       (5, 'Bob Checking Account', 4000.00);

INSERT INTO type (user_id, name)
VALUES (1, 'Family'),
       (2, 'Groceries'),
       (3, 'Entertainment'),
       (4, 'Rent'),
       (5, 'Utilities');

INSERT INTO transaction (from_account_id, to_account_id, amount, date)
VALUES (1, 3, 500.00, CURRENT_DATE),
       (NULL, 5, 200.00, CURRENT_DATE),
       (3, NULL, 150.00, CURRENT_DATE - 1),
       (5, 2, 120.00, CURRENT_DATE - 1),
       (4, 1, 1000.00, CURRENT_DATE);

INSERT INTO type_transaction (type_id, transaction_id)
VALUES ((SELECT id FROM type WHERE user_id = 3 AND name = 'Entertainment'), 1),
       ((SELECT id FROM type WHERE user_id = 5 AND name = 'Utilities'), 2),
       ((SELECT id FROM type WHERE user_id = 2 AND name = 'Groceries'), 4),
       ((SELECT id FROM type WHERE user_id = 1 AND name = 'Family'), 5);