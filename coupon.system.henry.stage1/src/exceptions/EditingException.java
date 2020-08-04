package exceptions;

public class EditingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	public EditingException() {
		super("Cannot update: please check your INPUT");
	}

}
