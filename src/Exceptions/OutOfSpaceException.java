package Exceptions;

/**
 * Created by pradeet on 11/4/16.
 */
public class OutOfSpaceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public OutOfSpaceException() {
        super();
    }

    public OutOfSpaceException(String message) {
        super(message);
    }
}
