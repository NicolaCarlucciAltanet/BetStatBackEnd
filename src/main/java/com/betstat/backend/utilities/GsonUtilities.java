package com.betstat.backend.utilities;

import com.betstat.backend.business.model.coupon.Coupon;
import com.google.gson.Gson;

public class GsonUtilities {

	/**
	 * Converte una stringa in un oggetto di tipo Coupon
	 * 
	 * @param couponString
	 * @return Coupon
	 */
	public static Coupon getCouponFromString(String couponString) {
		Gson gson = new Gson();
		return gson.fromJson(couponString, Coupon.class);
	}

	/**
	 * Converte un oggetto di tipo Coupon in json
	 * 
	 * @param coupon
	 * @return json del Coupon
	 */
	public static String getStringFromCoupon(Coupon coupon) {
		Gson gson = new Gson();
		return gson.toJson(coupon);
	}

}
