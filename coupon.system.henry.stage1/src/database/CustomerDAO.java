package database;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Customer;
import exceptions.CustomerNotFoundException;

public interface CustomerDAO {

	boolean isCustomerExists(String email, String password) throws SQLException, CustomerNotFoundException;

	void addCustomer(Customer customer) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	void deleteCustomer(int customerId) throws SQLException;

	ArrayList<Customer> getAllCustomers() throws SQLException;

	Customer getOneCustomer(int customerId) throws SQLException, CustomerNotFoundException;

}
