package exceptions;

public class CompanyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	public CompanyExistsException() {
		super("Company already exists!");
	}

}
