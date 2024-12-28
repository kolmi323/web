package web.CustomException;

public class AccountDataException extends RuntimeException {
    public AccountDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
