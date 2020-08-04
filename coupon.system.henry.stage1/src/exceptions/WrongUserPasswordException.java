package exceptions;

public class WrongUserPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;

	public WrongUserPasswordException() {
		super("Incorrect email or password, try again.");
	}

}
