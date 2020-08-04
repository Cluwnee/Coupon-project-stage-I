package database;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Coupon;
import exceptions.CouponNotFoundException;

public interface CouponDAO {

	void addCoupon(Coupon coupon) throws SQLException;

	void updateCoupon(Coupon coupon) throws SQLException;

	void deleteCoupon(int couponId) throws SQLException;

	ArrayList<Coupon> getAllCoupons() throws SQLException;

	Coupon getOneCoupon(int couponId) throws SQLException, CouponNotFoundException;

	void addCouponPurchase(int customerId, int couponId) throws SQLException;

	void deleteCouponPurchase(int customerId, int couponId) throws SQLException;

	void deleteCouponPurchase(int couponId) throws SQLException;

	ArrayList<Coupon> getAllCustomerCoupons(int customerId) throws SQLException;

	ArrayList<Coupon> getAllCompanyCoupons(int companyId) throws SQLException;
}
