package Exceptions;

/**
 * Created by pradeet on 11/4/16.
 */
public class DirectoryNotEmptyException extends Throwable {


    public DirectoryNotEmptyException() {
        super();
    }

    public DirectoryNotEmptyException(String message) {
        super(message);
    }
}
