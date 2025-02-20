package ru.gnezdilov.dao.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
