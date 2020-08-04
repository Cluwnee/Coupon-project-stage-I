package testing;

import java.sql.SQLException;
import beans.CategoryType;
import beans.Company;
import beans.Customer;
import database.CompanyDBDAO;
import database.ConnectionPool;
import database.CustomerDBDAO;
import exceptions.CompanyNotFoundException;
import exceptions.CustomerNotFoundException;

@SuppressWarnings("unused")
public class DatabaseTesting {

	public static void main(String[] args) {

		CategoryType myType = CategoryType.Clothing;
//		System.out.println(myType);
		System.out.println(myType.ordinal());
//
//		CategoryType myType2 = CategoryType.valueOf("food");
//		System.out.println(myType2);

//      ------------------------------------------------------------------------------------		

//		CompanyDBDAO compDB = new CompanyDBDAO();
//		try {
//			compDB.addCompany(new Company("Boba-Bola", "bob@boba-bola.com", "C0caC0l4"));
//			compDB.addCompany(new Company(name, companyEmail, companyPassword));
//			System.out.println("GET ALL: ");
//			System.out.println(compDB.getAllCompanies());
//
//		} catch (SQLException e) {
//			System.out.println("Add failed! " + e.getMessage());
//		}
//
//		try {
//			System.out.println("GET ONE: ");
//			System.out.println(compDB.getOneCompany(2));
//		} catch (SQLException | CompanyNotFoundException e) {
//			System.out.println("ERROR: " + e.getMessage());
//		}
//
//		try {
//			System.out.println(compDB.isCompanyExists("bob@boba-bola.com", "C0caC0l4"));
//		} catch (SQLException | CompanyNotFoundException e) {
//			System.out.println("ERROR: " + e.getMessage());
//		}

//		CustomerDBDAO custDB = new CustomerDBDAO();
//		try {
//			custDB.addCustomer(new Customer("John", "Doe", "john@doe.com", "4L1v3to0"));
//			System.out.println("GET ALL: ");
//			System.out.println(custDB.getAllCustomers());
//		} catch (SQLException e) {
//			System.out.println("Add failed! " + e.getMessage());
//		}
//
//		try {
//			System.out.println("GET ONE: ");
//			System.out.println(custDB.getOneCustomer(2));
//		} catch (SQLException | CustomerNotFoundException e) {
//			System.out.println("ERROR: " + e.getMessage());
//		}
//
//		try {
//			System.out.println(custDB.isCustomerExists("john@doe.com", "4L1v3to0"));
//		} catch (SQLException | CustomerNotFoundException e) {
//			System.out.println("ERROR: " + e.getMessage());
//		}

		ConnectionPool.getInstance().closeAllConnections();

	}

}
