package database;

import java.sql.SQLException;
import java.util.ArrayList;
import beans.Company;
import exceptions.CompanyNotFoundException;

public interface CompanyDAO {

	boolean isCompanyExists(String email, String password) throws SQLException, CompanyNotFoundException;

	void addCompany(Company company) throws SQLException;

	void updateCompany(Company company) throws SQLException;

	void deleteCompany(int companyId) throws SQLException;

	ArrayList<Company> getAllCompanies() throws SQLException;

	Company getOneCompany(int companyId) throws SQLException, CompanyNotFoundException;

}
