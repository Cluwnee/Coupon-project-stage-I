package facades;

import java.sql.SQLException;

import database.CompanyDBDAO;
import database.CouponDBDAO;
import database.CustomerDBDAO;
import exceptions.CompanyNotFoundException;
import exceptions.CustomerNotFoundException;

public abstract class ClientFacade {

	protected CompanyDBDAO companiesDAO = new CompanyDBDAO();
	protected CustomerDBDAO customersDAO = new CustomerDBDAO();
	protected CouponDBDAO couponsDAO = new CouponDBDAO();

	public abstract boolean login(String email, String password)
			throws SQLException, CompanyNotFoundException, CustomerNotFoundException;

}
