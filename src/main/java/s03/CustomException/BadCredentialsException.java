package s03.CustomException;

public class BadCredentialsException extends Exception{
    public BadCredentialsException() {
        super("Bad credentials");
    }
}
