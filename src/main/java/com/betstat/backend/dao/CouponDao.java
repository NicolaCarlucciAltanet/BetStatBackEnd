package com.betstat.backend.dao;

public class CouponDao {
	
	private String urlDb;
	
	private String user;
	
	private String password;
	
	private String drivers;

	public CouponDao(String urlDb, String user, String password, String drivers) {
		this.urlDb = urlDb;
		this.user = user;
		this.password = password;
		this.drivers = drivers;
		loadDriver();
	}

	private void loadDriver(){
		ConfigDao.loadDrivers(drivers);
	}
	private void getCopupon(String codCoupon) {
	}
	
}
