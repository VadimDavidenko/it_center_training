package taxi.exception;

/**
 * Created by Вадим on 28.02.2016.
 */
public class AuthenticationException extends Exception {
    private String message;

    public AuthenticationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
