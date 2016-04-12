package Exceptions;

/**
 * Created by pradeet on 11/4/16.
 */
public class OutOfSpaceException extends Exception {
    public OutOfSpaceException() {
        super();
    }

    public OutOfSpaceException(String message) {
        super(message);
    }
}
