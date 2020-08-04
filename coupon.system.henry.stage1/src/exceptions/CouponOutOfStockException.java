package exceptions;

public class CouponOutOfStockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;

	public CouponOutOfStockException() {
		super("Coupon out of stock!");
	}

}
