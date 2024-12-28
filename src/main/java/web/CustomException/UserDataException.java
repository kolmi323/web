package web.CustomException;

public class UserDataException extends RuntimeException {
    public UserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
