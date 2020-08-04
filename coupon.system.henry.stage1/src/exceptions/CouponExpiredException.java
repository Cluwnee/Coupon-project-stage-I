package exceptions;

public class CouponExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;

	public CouponExpiredException() {
		super("Sorry, this coupon has expired.");
	}

}
