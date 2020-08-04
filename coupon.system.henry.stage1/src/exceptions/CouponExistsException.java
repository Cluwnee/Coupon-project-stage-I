package exceptions;

public class CouponExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;

	public CouponExistsException() {
		super("Coupon already exists!");
	}
}
