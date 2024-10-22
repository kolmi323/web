INSERT INTO users (name, email, password) VALUES
    ('John Doe', 'john.doe@example.com', 'pass1234'),
    ('Jane Smith', 'jane.smith@example.com', 'pass5678'),
    ('Mike Johnson', 'mike.johnson@example.com', 'pass9101'),
    ('Alice Williams', 'alice.williams@example.com', 'pass1121'),
    ('Bob Brown', 'bob.brown@example.com', 'pass1314');

INSERT INTO account (user_id, name, balance) VALUES
    (1, 'John Savings Account', 5000.00),
    (1, 'John Checking Account', 3000.00),
    (2, 'Jane Savings Account', 6000.00),
    (3, 'Mike Checking Account', 1000.00),
    (4, 'Alice Investment Account', 15000.00),
    (4, 'Alice Savings Account', 12000.00),
    (5, 'Bob Checking Account', 4000.00);

INSERT INTO type (name) VALUES
    ('Family'),
    ('Groceries'),
    ('Entertainment'),
    ('Rent'),
    ('Utilities');

INSERT INTO transaction (from_account_id, to_account_id, amount, date_transaction) VALUES
    (1, 3, 500.00, '24.10.22'),
    (2, 5, 200.00, '24.10.22'),
    (3, 4, 150.00, '24.10.21'),
    (5, 2, 120.00, '24.10.21'),
    (4, 1, 1000.00, '24.10.22');

INSERT INTO type_transaction (type_id, transaction_id) VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 3),
    (2, 4),
    (5, 4),
    (1, 5);