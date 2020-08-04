package testing;

// when testing and you need an exception and its not here try to import, if no import is available that means I didn't make an exception to that :) oops
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import beans.CategoryType;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CompanyExistsException;
import exceptions.CompanyIDMismatchException;
import exceptions.CompanyNotFoundException;
import exceptions.CouponExistsException;
import exceptions.CouponExpiredException;
import exceptions.CouponNotFoundException;
import exceptions.CouponOutOfStockException;
import exceptions.CustomerExistsException;
import exceptions.CustomerNotFoundException;
import exceptions.EditingException;
import exceptions.WrongUserPasswordException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import job.CouponExpirationDailyJob;
import login.manager.ClientType;
import login.manager.LoginManager;

@SuppressWarnings("unused") // to stop IMPORT from annoying.
public class TestAll {

	public static void main(String[] args) {

		// THREAD TESTING: (works)

		CouponExpirationDailyJob job = new CouponExpirationDailyJob();
		Thread t1 = new Thread(job);
		t1.start();

		// COMPANY FACADE TESTING: (everything works)

		try {
			CompanyFacade cof = (CompanyFacade) LoginManager.getInstance().login("bob@boba-bola.com", "C0caC0l4",
					ClientType.Company);

			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();

			calStart.set(2020, Calendar.FEBRUARY, 10);
			calEnd.set(2020, Calendar.FEBRUARY, 25);

//          WORKS
//			cof.addCoupon(new Coupon(cof.getCompanyID(), CategoryType.Restaurants, "Boba-Bola Drink Festival",
//					"25% off for all Boba-Bola drinks using this coupon!", new Date(calStart.getTimeInMillis()),
//					new Date(calEnd.getTimeInMillis()), 1000, 35.00,
//					"https://66.media.tumblr.com/0554a64abdf51afe18c9cf644ce55b44/tumblr_pbx10ccK061w0943ao2_500.png"));

			calStart.set(2020, Calendar.FEBRUARY, 20);
			calEnd.set(2020, Calendar.MARCH, 5);

//			WORKS
//			cof.addCoupon(new Coupon(cof.getCompanyID(), CategoryType.Restaurants, "Boba-Bola Sugar Rush",
//					"30% off on all Boba-Bola drinks in the Boba-Bola store factory",
//					new Date(calStart.getTimeInMillis()), new Date(calEnd.getTimeInMillis()), 2000, 50.00,
//					"https://www.middleeasteye.net/sites/default/files/styles/article_page/public/main-images/000_Nic6328359.jpg?itok=VZA9ROBH"));

//			cof.addCoupon(new Coupon(cof.getCompanyID(), CategoryType.Clothing, "Boba-Bola T-Shirts discount",
//					"30% Boba-Bola T-Shirts using this coupon!", new Date(calStart.getTimeInMillis()),
//					new Date(calEnd.getTimeInMillis()), 300, 10.00,
//					"https://pics.onsizzle.com/can-have-coca-cola-sir-this-is-barber-shop-i-13276799.png"));

//			WORKS
//			Coupon coupon = cof.getOneCompanyCoupon(11);
//			coupon.setPrice(35);
//			cof.updateCoupon(coupon);

//			WORKS
//			cof.deleteCoupon(11);

//			WORKS
//			System.out.println(cof.getOneCompanyCoupon(10));
//			System.out.println(cof.getCompanyCoupons());
//			System.out.println(cof.getCompanyCoupons(CategoryType.Clothing));
//			System.out.println(cof.getCompanyCoupons(40));
//			System.out.println(cof.getCompanyDetails());

			// CUSTOMER FACADE TESTING: (everything works)

			CustomerFacade cuf = (CustomerFacade) LoginManager.getInstance().login("jane@doe.com", "4L1v3",
					ClientType.Customer);

//			WORKS
//			Coupon coupon = cof.getOneCompanyCoupon(504);
//			cuf.purchaseCoupon(coupon);
//			System.out.println(cuf.getCustomerCoupons());
//			System.out.println(cuf.getCustomerCoupons(CategoryType.Clothing));
//			System.out.println(cuf.getCustomerCoupons(30));
//			System.out.println(cuf.getCustomerDetails());

			// ADMIN FACADE TESTING: (everything works)

			AdminFacade af = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin",
					ClientType.Administrator);

//			af.addCompany(new Company("Scammers.inc", "notascam@scam.com", "Scam4Money"));
//			Company company = af.getOneCompany(3);
//			company.setCompanyPassword("MoneyScam");
//			af.updateCompany(company);

//			Company company = af.getOneCompany(4);
//			company.setCompanyPassword("newP4ss");
//			af.updateCompany(company);

//			af.deleteCompany(4);
//			System.out.println(af.getAllCompanies());
//			System.out.println(af.getOneCompany(4));

//			af.addCustomer(new Customer("Bobo", "the Hobo", "aintgotone@homeless.com", "Hobo4Life"));
//			Customer customer = af.getOneCustomer(3);
//			customer.setEmail("notlong@walla.co.il");
//			af.updateCustomer(customer);

//			Customer customer = af.getOneCustomer(2);
//			customer.setFirstName("Johnny");
//			af.updateCustomer(customer);

//			af.deleteCustomer(3);
//			System.out.println(af.getAllCustomers());
//			System.out.println(af.getOneCustomer(2));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		job.stopJob(); // end the thread
		t1.interrupt(); // interrupt the sleep
	}

}
