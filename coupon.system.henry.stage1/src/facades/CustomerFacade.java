package facades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import beans.CategoryType;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponExpiredException;
import exceptions.CouponOutOfStockException;
import exceptions.CustomerAlreadyBoughtException;
import exceptions.CustomerNotFoundException;

public class CustomerFacade extends ClientFacade {

	private int customerID;

	public CustomerFacade() {

	}

	@Override
	// same as company facade only here we compare customer details
	public boolean login(String email, String password) throws SQLException, CustomerNotFoundException {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(email) && cust.getPassword().equals(password))
				customerID = cust.getCustomerId();
			return true;
		}
		return false;
	}

	// lets the customer purchase a coupon.
	public void purchaseCoupon(Coupon coupon)
			throws SQLException, CouponOutOfStockException, CouponExpiredException, CustomerAlreadyBoughtException {
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		for (Coupon coup : getCustomerCoupons()) { // first check to see if customer already bought said coupon
			if (coup.getCouponId() == coupon.getCouponId()) {
				throw new CustomerAlreadyBoughtException();
			}
		}
		if (coupon.getAmount() <= 0) { // second check to see if coupon is in stock
			throw new CouponOutOfStockException();
		} else if (coupon.getEndDate().before(now)) { // third check to see if coupon has expired
			throw new CouponExpiredException();
		} else { // if all checks passed then customer may purchase the coupon, we decrease the
					// amount by 1 and update the amount and add the purchase.
			coupon.setAmount(coupon.getAmount() - 1);
			couponsDAO.updateCoupon(coupon);
			couponsDAO.addCouponPurchase(customerID, coupon.getCouponId());
		}
	}

	public ArrayList<Coupon> getCustomerCoupons() throws SQLException {
		return couponsDAO.getAllCustomerCoupons(customerID);
	}

	// same as company facade this will run a check by category on all the customers
	// coupons and return by desired category
	public ArrayList<Coupon> getCustomerCoupons(CategoryType category) throws SQLException {
		ArrayList<Coupon> cateCoupons = new ArrayList<Coupon>();
		for (Coupon coup : getCustomerCoupons()) {
			if (coup.getCategory().ordinal() == category.ordinal()) {
				cateCoupons.add(coup);
			}
		}
		return cateCoupons;
	}

	// same as above only with max price
	public ArrayList<Coupon> getCustomerCoupons(int maxPrice) throws SQLException {
		ArrayList<Coupon> priceCoupons = new ArrayList<Coupon>();
		for (Coupon coup : getCustomerCoupons()) {
			if (coup.getPrice() < maxPrice) {
				priceCoupons.add(coup);
			}
		}
		return priceCoupons;
	}

	public Customer getCustomerDetails() throws SQLException, CustomerNotFoundException {
		return customersDAO.getOneCustomer(customerID);
	}

}
