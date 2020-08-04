package login.manager;

import java.sql.SQLException;

import exceptions.CompanyNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.WrongUserPasswordException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;

public class LoginManager {

	private static LoginManager instance = new LoginManager(); // singleton cause we only want one instance of login

	private LoginManager() {

	}

	public static LoginManager getInstance() {
		return instance;
	}

	public ClientFacade login(String email, String password, ClientType clientType)
			throws SQLException, WrongUserPasswordException, CompanyNotFoundException, CustomerNotFoundException {

		// we check who is logging in
		switch (clientType) {
		case Administrator:
			AdminFacade af = new AdminFacade();
			if (af.login(email, password)) // check to see if the entered info matches
				return af; // if true then return ADMIN facade
			else
				throw new WrongUserPasswordException(); // else throw exception that email or password were wrong

		case Company:
			CompanyFacade cof = new CompanyFacade(); // same as ADMIN
			if (cof.login(email, password))
				return cof;
			else
				throw new WrongUserPasswordException();

		case Customer:
			CustomerFacade cuf = new CustomerFacade(); // same as ADMIN
			if (cuf.login(email, password))
				return cuf;
			else
				throw new WrongUserPasswordException();
		}
		return null;
	}

}
