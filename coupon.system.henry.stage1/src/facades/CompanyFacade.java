package facades;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CompanyIDMismatchException;
import exceptions.CompanyNotFoundException;
import exceptions.CouponExistsException;
import exceptions.CouponNotFoundException;

public class CompanyFacade extends ClientFacade {

	private int companyID;

	public CompanyFacade() {

	}

	public int getCompanyID() {
		return companyID;
	}

	@Override
	// Login by comparing customer email and password to existing companies and
	// returning their id to use
	public boolean login(String email, String password) throws SQLException, CompanyNotFoundException {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getCompanyEmail().equals(email) && comp.getCompanyPassword().equals(password)) { // companiesDAO.isCompanyExists
																										// can be used
																										// here too :)
				companyID = comp.getCompanyId();
				return true;
			}
		}
		return false;
	}

	// lets the company add a new coupon for purchase, we compare title and company
	// id so the same company can't create the same coupon but another company can.
	public void addCoupon(Coupon coupon) throws SQLException, CouponExistsException {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon coup : coupons) {
			if (coup.getCompanyId() == companyID && coup.getTitle().equals(coupon.getTitle())) {
				throw new CouponExistsException();
			}
		}
		couponsDAO.addCoupon(coupon);
	}

	// lets the company update an existing coupon, we run an array list for all
	// existing company coupons and then compare the title of the one we want to
	// update, we also check for company id to make sure the company is updating its
	// own coupon and not another company's
	public void updateCoupon(Coupon coupon) throws SQLException, CouponNotFoundException {
		boolean exception = true;
		ArrayList<Coupon> coupons = couponsDAO.getAllCompanyCoupons(companyID);
		for (Coupon coup : coupons) {
			if (coup.getTitle().equals(coupon.getTitle()) && coup.getCouponId() == coupon.getCouponId()) {
				couponsDAO.updateCoupon(coupon);
				exception = false;
				break;
			}
		}
		if (exception == true) {
			throw new CouponNotFoundException();
		}
	}

	// lets the company delete and existing coupon, when we delete the coupon we
	// also need to delete it's purchase history,
	// that is why we also run an array list of all existing customers.
	// we check to see if company ID and the coupon ID which we want to delete match
	// after that we run all the customers histories and delete them where the
	// coupon ID exists.
	// we use a boolean exception to check in the end if we found the coupon / if
	// the company is trying to accidently delete another company's coupon
	public void deleteCoupon(int couponId) throws SQLException, CompanyIDMismatchException {
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		boolean exception = true;
		for (Coupon coup : coupons) {
			if (coup.getCompanyId() == companyID && coup.getCouponId() == couponId) {
				for (Customer cust : customers) {
					couponsDAO.deleteCouponPurchase(cust.getCustomerId(), couponId);
				}
				couponsDAO.deleteCoupon(couponId);
				exception = false;
				break;
			}
		}
		if (exception == true)
			throw new CompanyIDMismatchException();
	}

	public Coupon getOneCompanyCoupon(int couponId) throws SQLException, CouponNotFoundException {
		return couponsDAO.getOneCoupon(couponId);
	}

	public ArrayList<Coupon> getCompanyCoupons() throws SQLException {
		return couponsDAO.getAllCompanyCoupons(companyID);
	}

	// we create a new array list to populate by category and run in a loop on all
	// the companies coupons by using the getCompanyCoupons command,
	// we then compare the category ordinal of the coupon to see if it matches the
	// entered category.
	public ArrayList<Coupon> getCompanyCoupons(CategoryType category) throws SQLException {
		ArrayList<Coupon> cateCoupons = new ArrayList<Coupon>();
		for (Coupon coup : getCompanyCoupons()) {
			if (coup.getCategory().ordinal() == category.ordinal())
				cateCoupons.add(coup);
		}
		return cateCoupons;
	}

	// same as above only with a maximum price.
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws SQLException {
		ArrayList<Coupon> priceCoupons = new ArrayList<Coupon>();
		for (Coupon coup : getCompanyCoupons()) {
			if (coup.getPrice() < maxPrice)
				priceCoupons.add(coup);
		}
		return priceCoupons;
	}

	public Company getCompanyDetails() throws SQLException, CompanyNotFoundException {
		return companiesDAO.getOneCompany(companyID);
	}

}
