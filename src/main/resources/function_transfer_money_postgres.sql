CREATE OR REPLACE FUNCTION transfer_money(
    v_from_account_id INT,
    v_to_account_id INT,
    v_amount DECIMAL(20,2)) RETURNS INT AS $$
DECLARE
    new_transaction_id INT;
BEGIN
    UPDATE account
    SET balance = balance - v_amount
    WHERE id = v_from_account_id;
    UPDATE account
    SET balance = balance + v_amount
    WHERE id = v_to_account_id;
    INSERT INTO transaction (from_account_id, to_account_id, amount, date)
    VALUES (v_from_account_id, v_to_account_id, v_amount, CURRENT_DATE)
        RETURNING id INTO new_transaction_id;
    RETURN new_transaction_id;
END $$ LANGUAGE plpgsql;