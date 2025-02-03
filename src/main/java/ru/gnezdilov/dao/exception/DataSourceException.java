package ru.gnezdilov.dao.exception;

public class DataSourceException extends RuntimeException {
    public DataSourceException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
