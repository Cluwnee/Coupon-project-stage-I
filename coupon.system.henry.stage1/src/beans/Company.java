package beans;

import java.util.ArrayList;

public class Company {

	private int companyId;
	private String name;
	private String companyEmail;
	private String companyPassword;
	private ArrayList<Coupon> coupons;

	// for creation
	public Company(String name, String companyEmail, String companyPassword) {
		super();
		this.name = name;
		this.companyEmail = companyEmail;
		this.companyPassword = companyPassword;
	}

	// for updating / use in DBDAO
	public Company(int companyId, String name, String companyEmail, String companyPassword) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.companyEmail = companyEmail;
		this.companyPassword = companyPassword;
	}

	public int getCompanyId() {
		return companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", companyEmail=" + companyEmail
				+ ", companyPassword=" + companyPassword + "]";
	}

}
