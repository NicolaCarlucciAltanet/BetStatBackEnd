package com.betstat.backend.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betstat.backend.controller.coupon.CouponController;
import com.betstat.backend.model.coupon.Coupon;
import com.betstat.backend.utilities.DateUtilities;
import com.betstat.backend.utilities.UtilitiesConstantProperties;

@Component
public class ServicesCoupon {

	final static Logger logger = LogManager.getLogger(CouponController.class);

	@Value("${" + UtilitiesConstantProperties.COUPON_DATA_NAME + "}")
	private String couponDataName;

	@Value("${" + UtilitiesConstantProperties.COUPON_DATA_CLASS + "}")
	private String couponDataClass;

	@Value("${" + UtilitiesConstantProperties.COUPON_DATA_ENDLENGTHCLASS + "}")
	private String couponDataEnd;

	public void readSourcePageHtml(String path, String nameFile) {
		logger.info("START readSourcePageHtml");
		try {
			URL urlObject = new URL(path + File.separator + nameFile);
			URLConnection urlConnection = urlObject.openConnection();
			urlConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			// StringBuilder stringBuilder = new StringBuilder();
			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
				String inputLine;
				Coupon coupon = new Coupon();
				// StringBuilder stringBuilder = new StringBuilder();
				while ((inputLine = bufferedReader.readLine()) != null) {
					if (inputLine.contains(couponDataName)) {
						int n = inputLine.indexOf(couponDataClass);
						String dataCouponString = inputLine.substring(n + (Integer.valueOf(couponDataEnd)),
								inputLine.length());
						String[] dataCouponStringSplitb = dataCouponString.split("<");
						Date data_coupon = DateUtilities.elaborateDate(dataCouponStringSplitb[0]);
						coupon.setData_coupon(data_coupon);
						System.out.println(coupon.getData_coupon().toString());

					}
					// stringBuilder.append(inputLine);
				}

				// return stringBuilder.toString();
			}
		} catch (Exception exception) {

		}

//		OKResponse okResponse = new OKResponse();
////		if(okResponse instanceof OKResponse.class)
//		return okResponse;
	}
}
