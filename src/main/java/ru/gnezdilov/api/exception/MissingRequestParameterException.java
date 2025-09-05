package ru.gnezdilov.api.exception;

public class MissingRequestParameterException extends RuntimeException {
    public MissingRequestParameterException(String parameterName) {
        super("Missing request parameter: " + parameterName);
    }
}
