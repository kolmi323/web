create table users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(10) NOT NULL
);

create table account (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) NOT NULL,
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(20, 2) NOT NULL,
    UNIQUE (user_id, name)
);

create table type (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) NOT NULL ,
    name VARCHAR(255) NOT NULL,
    UNIQUE (user_id, name)
);

create table transaction (
    id SERIAL PRIMARY KEY,
    from_account_id INT REFERENCES account(id),
    to_account_id INT REFERENCES account(id),
    amount DECIMAL(20,2) NOT NULL,
    date DATE NOT NULL
);

create table type_transaction (
    id SERIAL PRIMARY KEY,
    type_id INT REFERENCES type(id) NOT NULL,
    transaction_id INT REFERENCES transaction(id) NOT NULL
);