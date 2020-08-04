package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.CategoryType;
import beans.Coupon;
import exceptions.CouponNotFoundException;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPool pool = ConnectionPool.getInstance();

	@Override
	public void addCoupon(Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();
		// add new coupon
		try {
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO coupons(company_id, category_id, title, description, start_date, end_date, amount, price, image) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setInt(1, coupon.getCompanyId()); // in testing we will add the logged in companies ID, without this the
													// coupon will be created with no company ID
			st.setInt(2, coupon.getCategory().ordinal() + 1); // make sure to return category not as STRING but as INT
																// ordinal or SQL will be angry. Also +1 because SQL
																// starts at 1 and JAVA starts at 0 so we calculate the
																// differences
			st.setString(3, coupon.getTitle());
			st.setString(4, coupon.getDescription());
			st.setDate(5, coupon.getStartDate());
			st.setDate(6, coupon.getEndDate());
			st.setInt(7, coupon.getAmount());
			st.setDouble(8, coupon.getPrice());
			st.setString(9, coupon.getImage());
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException {
		Connection con = pool.getConnection();
		// update existing coupon using it's ID
		try {
			PreparedStatement st = con.prepareStatement(
					"UPDATE coupons SET category_id = ?, title = ?, description = ?, start_date = ?, end_date = ?, amount = ?, price = ?, image = ? WHERE coupon_id = ?");
			st.setInt(1, coupon.getCategory().ordinal() + 1); // JAVA SQL differences
			st.setString(2, coupon.getTitle());
			st.setString(3, coupon.getDescription());
			st.setDate(4, coupon.getStartDate());
			st.setDate(5, coupon.getEndDate());
			st.setInt(6, coupon.getAmount());
			st.setDouble(7, coupon.getPrice());
			st.setString(8, coupon.getImage());
			st.setInt(9, coupon.getCouponId());
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCoupon(int couponId) throws SQLException {
		Connection con = pool.getConnection();
		// delete existing coupon using ID
		try {
			PreparedStatement st = con.prepareStatement("DELETE FROM coupons WHERE coupon_id = ?");
			st.setInt(1, couponId);
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() throws SQLException {
		// same as before empty array list to populate, update and return..
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		Connection con = pool.getConnection();
		try {
			// make sure to join categories from both tables
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM Coupons JOIN categories ON coupons.category_id = categories.category_id");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				// we must return the value of the category, SQL is set up to accept INT so we
				// must return INT (not returning STRING) and make up the differences between
				// JAVA and SQL by =1
				coupons.add(new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"),
						CategoryType.values()[rs.getInt("category_id") - 1], rs.getString("title"),
						rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"),
						rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return coupons;
		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public Coupon getOneCoupon(int couponId) throws SQLException, CouponNotFoundException {
		Connection con = pool.getConnection();

		try {
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM Coupons JOIN categories ON coupons.category_id = categories.category_id WHERE coupon_id = ?");
			st.setInt(1, couponId);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"),
						CategoryType.values()[rs.getInt("category_id") - 1], rs.getString("title"),
						rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"),
						rs.getInt("amount"), rs.getDouble("price"), rs.getString("image"));
			}
			throw new CouponNotFoundException();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void addCouponPurchase(int customerId, int couponId) throws SQLException {
		Connection con = pool.getConnection();
		// connects the customer and coupons, thus making a history of purchase or a
		// connection between the customer and coupon
		try {
			PreparedStatement st = con.prepareStatement("INSERT INTO customer_vs_coupons values(?,?)");
			st.setInt(1, customerId);
			st.setInt(2, couponId);
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
		Connection con = pool.getConnection();
		// deleting customer / coupon purchase history or refunding etc...
		// this method must accept both customer id and coupon id good for deleting a
		// known customer and his coupons
		try {
			PreparedStatement st = con
					.prepareStatement("DELETE FROM customer_vs_coupons WHERE customer_id = ? AND coupon_id = ?");
			st.setInt(1, customerId);
			st.setInt(2, couponId);
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	public void deleteCouponPurchase(int couponId) throws SQLException {
		Connection con = pool.getConnection();
		// this method only accepts coupon id, good when we just want to delete a
		// coupons history of purchase without regard to customers
		try {
			PreparedStatement st = con.prepareStatement("DELETE FROM customer_vs_coupons WHERE coupon_id = ?");
			st.setInt(1, couponId);
			st.execute();

		} finally {
			pool.restoreConnection(con);
		}
	}

	public ArrayList<Coupon> getAllCustomerCoupons(int customerId) throws SQLException {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		Connection con = pool.getConnection();
		try {
			// returns a specific customers coupons, make sure to join categories and
			// customer purchase history from customers vs coupons to return only his
			// specific purchases
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM Coupons JOIN categories ON coupons.category_id = categories.category_id"
							+ " JOIN customer_vs_coupons ON customer_vs_coupons.coupon_id = coupons.coupon_id WHERE customer_id = ?");
			st.setInt(1, customerId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				coupons.add(new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"),
						CategoryType.values()[rs.getInt("category_id") - 1], rs.getString("title"),
						rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"),
						rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return coupons;
		} finally {
			pool.restoreConnection(con);
		}
	}

	public ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws SQLException {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		Connection con = pool.getConnection();
		try {
			// same as the customer coupons above only this time for a company.
			// no need to join customer vs coupons only categories.
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM Coupons JOIN categories ON coupons.category_id = categories.category_id"
							+ " WHERE company_id = ?");
			st.setInt(1, companyId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				coupons.add(new Coupon(rs.getInt("coupon_id"), rs.getInt("company_id"),
						CategoryType.values()[rs.getInt("category_id") - 1], rs.getString("title"),
						rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"),
						rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
			}
			return coupons;
		} finally {
			pool.restoreConnection(con);
		}
	}

}
