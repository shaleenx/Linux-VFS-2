package Exceptions;

/**
 * Created by pradeet on 11/4/16.
 */
public class DirectoryNotEmptyException extends Throwable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public DirectoryNotEmptyException() {
        super();
    }

    public DirectoryNotEmptyException(String message) {
        super(message);
    }
}
