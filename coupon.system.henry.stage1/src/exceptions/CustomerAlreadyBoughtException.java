package exceptions;

public class CustomerAlreadyBoughtException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;

	public CustomerAlreadyBoughtException() {
		super("You already own this coupon!");
	}

}
