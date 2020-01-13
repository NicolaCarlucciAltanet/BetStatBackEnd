package com.betstat.backend.controller.coupon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.betstat.backend.business.ServicesCoupon;
import com.betstat.backend.dao.ConfigDao;
import com.betstat.backend.model.coupon.Coupon;
import com.betstat.backend.model.response.ModelResponse;
import com.betstat.backend.model.response.OKResponse;
import com.betstat.backend.utilities.GsonUtilities;
import com.betstat.backend.utilities.UtilitiesConstantProperties;

@Controller
@RequestMapping("/coupon/controller")
public class CouponController {

	final static Logger logger = LogManager.getLogger(CouponController.class);

	@Value("${" + UtilitiesConstantProperties.FILE_NAME + "}")
	private String fileName;

	@Value("${" + UtilitiesConstantProperties.FILE_PATH + "}")
	private String filePath;

	@Value("${" + UtilitiesConstantProperties.DB_STRING_CONNECTION + "}")
	private String dbStringConnection;

	@Autowired
	ServicesCoupon serviceCoupon;

	@GetMapping(value = "/readcoupon")
	public void readcouponController() {
		logger.info("START readcouponController");
		// ServicesCoupon serviceCoupon = new ServicesCoupon();
		ModelResponse modelResponse = serviceCoupon.readCouponFromHtml(filePath, fileName);
		if (modelResponse instanceof OKResponse) {
			logger.info(modelResponse.getDescription());
			Coupon coupon = GsonUtilities.getCouponFromString(modelResponse.getDescription());
			logger.info(coupon.toString());
		} else {
			logger.error(modelResponse.getDescription());
		}
		ConfigDao configDao = new ConfigDao();
		configDao.loadDrivers(dbStringConnection);
		logger.info("END readcouponController");
	}

}
