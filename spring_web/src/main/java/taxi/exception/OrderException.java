package taxi.exception;

/**
 * Created by Вадим on 28.02.2016.
 */
public class OrderException extends Exception {
    private String message;

    public OrderException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
