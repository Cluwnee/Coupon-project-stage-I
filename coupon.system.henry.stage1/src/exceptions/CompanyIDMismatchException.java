package exceptions;

public class CompanyIDMismatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;

	public CompanyIDMismatchException() {
		super("You do not have premmision to edit this coupon!");
	}

}
