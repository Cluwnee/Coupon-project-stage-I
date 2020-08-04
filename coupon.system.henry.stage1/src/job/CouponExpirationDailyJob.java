package job;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import beans.Coupon;
import database.CouponDAO;
import database.CouponDBDAO;

public class CouponExpirationDailyJob extends Thread implements Runnable {

	private CouponDAO coupons = new CouponDBDAO();
	private boolean quit;

	public CouponExpirationDailyJob() {
	}

	@Override
	public void run() {
		while (quit == false) { // while stopJob was not ordered it will keep running forever..
			Calendar cal = Calendar.getInstance();
			Date now = new Date(cal.getTimeInMillis());
			try {
				for (Coupon coup : coupons.getAllCoupons()) { // run on all the program coupons
					if (coup.getEndDate().before(now)) { // check to see if the date has already passed and if it did
															// then delete all history and coupon
						coupons.deleteCouponPurchase(coup.getCouponId());
						coupons.deleteCoupon(coup.getCouponId());
					}
				}
				Thread.sleep(86400000); // the thread runs once a day, this number equals to 24 hours
			} catch (SQLException | InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void stopJob() { // the command that will force the thread to shut down and not remain sleeping
							// in the background
		quit = true;
		interrupt();
	}
}
