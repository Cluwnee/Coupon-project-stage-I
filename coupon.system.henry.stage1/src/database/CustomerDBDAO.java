package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import beans.Customer;
import exceptions.CustomerNotFoundException;

public class CustomerDBDAO implements CustomerDAO {

	private ConnectionPool pool = ConnectionPool.getInstance();

	@Override
	public boolean isCustomerExists(String email, String password) throws SQLException, CustomerNotFoundException {
		Connection con = pool.getConnection();

		try {
			PreparedStatement st = con
					.prepareStatement("SELECT * FROM customers WHERE customer_email = ? and customer_password = ?");
			st.setString(1, email);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}
			throw new CustomerNotFoundException();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void addCustomer(Customer customer) throws SQLException {
		Connection con = pool.getConnection();
		// add new customer
		try {
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO customers(first_name, last_name, customer_email, customer_password) values(?, ?, ?, ?)");
			st.setString(1, customer.getFirstName());
			st.setString(2, customer.getLastName());
			st.setString(3, customer.getEmail());
			st.setString(4, customer.getPassword());
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		Connection con = pool.getConnection();
		// update existing customer using id
		try {
			PreparedStatement st = con.prepareStatement(
					"UPDATE customers SET first_name = ?, last_name = ?, customer_email = ?, customer_password = ? WHERE customer_id = ?");
			st.setString(1, customer.getFirstName());
			st.setString(2, customer.getLastName());
			st.setString(3, customer.getEmail());
			st.setString(4, customer.getPassword());
			st.setInt(5, customer.getCustomerId());
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCustomer(int customerId) throws SQLException {
		Connection con = pool.getConnection();
		// delete existing customer
		try {
			PreparedStatement st = con.prepareStatement("DELETE FROM customers WHERE customer_id = ?");
			st.setInt(1, customerId);
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		// same as get all companies, create a new array list to populate, update and
		// return..
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Connection con = pool.getConnection();
		try {

			PreparedStatement st = con.prepareStatement("SELECT * FROM customers");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				customers.add(new Customer(rs.getInt("customer_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("customer_email"), rs.getString("customer_password")));
			}
			return customers;
		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public Customer getOneCustomer(int customerId) throws SQLException, CustomerNotFoundException {
		Connection con = pool.getConnection();
		// same as companies give a customer id and check if it exists and has values
		// then return..
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
			st.setInt(1, customerId);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return new Customer(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("customer_email"), rs.getString("customer_password"));
			}
			throw new CustomerNotFoundException();

		} finally {
			pool.restoreConnection(con);
		}

	}

}
