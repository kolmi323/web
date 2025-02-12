package ru.gnezdilov.dao.exception;

public class ExitException extends RuntimeException {
    public ExitException() {
        super("You canceled the Answer entry");
    }
}
