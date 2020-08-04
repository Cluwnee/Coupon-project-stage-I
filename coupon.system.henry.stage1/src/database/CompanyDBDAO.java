package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Company;
import exceptions.CompanyNotFoundException;

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool = ConnectionPool.getInstance();

	@Override
	public boolean isCompanyExists(String email, String password) throws SQLException, CompanyNotFoundException {

		Connection con = pool.getConnection();

		try {
			PreparedStatement st = con
					.prepareStatement("SELECT * FROM companies WHERE company_email = ? and company_password = ?");
			st.setString(1, email);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}
			throw new CompanyNotFoundException();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void addCompany(Company company) throws SQLException {

		Connection con = pool.getConnection();

		try {
			// add a new company
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO companies(company_name, company_email, company_password) values(?, ?, ?)");
			st.setString(1, company.getName());
			st.setString(2, company.getCompanyEmail());
			st.setString(3, company.getCompanyPassword());
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void updateCompany(Company company) throws SQLException {
		// update existing company
		Connection con = pool.getConnection();

		try {
			PreparedStatement st = con.prepareStatement(
					"UPDATE companies SET company_email = ?, company_password = ? where company_id = ?");
			st.setString(1, company.getCompanyEmail());
			st.setString(2, company.getCompanyPassword());
			st.setInt(3, company.getCompanyId());
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCompany(int companyId) throws SQLException {
		// delete existing company
		Connection con = pool.getConnection();

		try {
			PreparedStatement st = con.prepareStatement("DELETE FROM companies WHERE company_id = ?");
			st.setInt(1, companyId);
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>(); // create new array list to populate and return
																	// (constantly updated)
		Connection con = pool.getConnection();
		try {
			// keep selecting a new company and run on all their values and add them to the
			// array list
			PreparedStatement st = con.prepareStatement("SELECT * FROM companies");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				companies.add(new Company(rs.getInt("company_id"), rs.getString("company_name"),
						rs.getString("company_email"), rs.getString("company_password")));
			}
			return companies;
		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public Company getOneCompany(int companyId) throws SQLException, CompanyNotFoundException {
		Connection con = pool.getConnection();
		// select a single company using their id, if they exist run on their values and
		// return
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM companies WHERE company_id = ?");
			st.setInt(1, companyId);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return new Company(rs.getInt("company_id"), rs.getString("company_name"), rs.getString("company_email"),
						rs.getString("company_password"));
			}
			throw new CompanyNotFoundException();

		} finally {
			pool.restoreConnection(con);
		}
	}

}
