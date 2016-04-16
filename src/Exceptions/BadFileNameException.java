package Exceptions;

/**
 * Created by pradeet on 11/4/16.
 */
public class BadFileNameException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadFileNameException(String message) {
        super(message);
    }

    public BadFileNameException() {
        super("Invalid File Name");
    }

    public BadFileNameException(Throwable cause) {
        super(cause);
    }

    public BadFileNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
