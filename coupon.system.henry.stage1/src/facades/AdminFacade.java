package facades;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CompanyExistsException;
import exceptions.CompanyNotFoundException;
import exceptions.CustomerExistsException;
import exceptions.CustomerNotFoundException;
import exceptions.EditingException;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		super();
	}

	@Override
	// user must login with this specific email and password to to receive ADMIN
	// privileges
	public boolean login(String email, String password) throws SQLException {
		if (email.contentEquals("admin@admin.com") && password.contentEquals("admin")) {
			return true;
		}
		return false;
	}

	// lets the ADMIN create a new company, array list and loop to check if a
	// company already exists so no duplicate companies can be created
	public void addCompany(Company company) throws SQLException, CompanyExistsException {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getName().equals(company.getName()) || comp.getCompanyEmail().equals(company.getCompanyEmail())) {
				throw new CompanyExistsException();
			}
		}
		companiesDAO.addCompany(company);
	}

	// lets the ADMIN update an existing company, we run a check to see if the
	// updated company is the same company so the ADMIN can't accidently have the
	// same company name twice with different ID's
	public void updateCompany(Company company) throws SQLException, EditingException {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		for (Company comp : companies) {
			if (comp.getName().equals(company.getName()) && comp.getCompanyId() != company.getCompanyId()) {
				throw new EditingException();
			}
		}
		companiesDAO.updateCompany(company);
	}

	// lets the ADMIN delete an existing company, when we delete a company we must
	// also delete all their coupons and purchase history.
	// we use an exception boolean to see if a company is found, if it's found it
	// will be deleted (history + coupons included) but if not the exception stays
	// true and the program will
	// throw an exception
	public void deleteCompany(int companyId) throws SQLException, CompanyNotFoundException {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		ArrayList<Coupon> coupons = couponsDAO.getAllCoupons();
		boolean exception = true;
		for (Company comp : companies) {
			if (companyId == comp.getCompanyId()) {
				for (Coupon coup : coupons) {
					if (coup.getCompanyId() == companyId) {
						couponsDAO.deleteCouponPurchase(coup.getCouponId());
						couponsDAO.deleteCoupon(coup.getCouponId());
					}
				}
				companiesDAO.deleteCompany(companyId);
				exception = false;
				break;
			}
		}
		if (exception)
			throw new CompanyNotFoundException();
	}

	// returns all existing companies, we use the DB getAllCompanies command to make
	// life simple
	public ArrayList<Company> getAllCompanies() throws SQLException {
		ArrayList<Company> companies = companiesDAO.getAllCompanies();
		return companies;
	}

	// returns a specific company using their ID, we use the DB getOneCompany
	// command to make life simple
	public Company getOneCompany(int companyId) throws SQLException, CompanyNotFoundException {
		return companiesDAO.getOneCompany(companyId);
	}

	// lets the ADMIN add a new customer, we check email if customer already exists
	public void addCustomer(Customer customer) throws SQLException, CustomerExistsException {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail())) {
				throw new CustomerExistsException();
			}
		}
		customersDAO.addCustomer(customer);
	}

	// lets the ADMIN update an existing customer, we make sure to check the email
	// and customer ID to make sure we are updating the correct customer and not
	// adding or updating something else
	public void updateCustomer(Customer customer) throws SQLException, EditingException {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()) && cust.getCustomerId() != customer.getCustomerId())
				throw new EditingException();
		}
		customersDAO.updateCustomer(customer);
	}

	// lets the ADMIN delete and existing customer, we must also delete customer
	// purchase history which is why we will run an array list for all his coupons.
	// we will run on all customers until we match an ID and then run on all his
	// coupons and delete his purchase history, finally deleting the customer.
	// we use an exception boolean to check if the customer actually exists.
	public void deleteCustomer(int customerId) throws SQLException, CustomerNotFoundException {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		ArrayList<Coupon> coupons = couponsDAO.getAllCustomerCoupons(customerId);
		boolean exception = true;
		for (Customer cust : customers) {
			if (cust.getCustomerId() == customerId) {
				for (Coupon coup : coupons) {
					couponsDAO.deleteCouponPurchase(customerId, coup.getCouponId());
				}
				customersDAO.deleteCustomer(customerId);
				exception = false;
				break;
			}
		}
		if (exception == true)
			throw new CustomerNotFoundException();
	}

	// returns all the existing customers, we we the DB to make life easier and use
	// the command of getAllCustomers.
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		ArrayList<Customer> customers = customersDAO.getAllCustomers();
		return customers;
	}

	// returns a specific customer using their ID, again we use DB to make life
	// easier and use the command getOneCustomer.
	public Customer getOneCustomer(int customerId) throws SQLException, CustomerNotFoundException {
		return customersDAO.getOneCustomer(customerId);
	}

}