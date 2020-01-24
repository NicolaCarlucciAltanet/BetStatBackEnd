package com.betstat.backend.controller.coupon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.betstat.backend.business.model.coupon.Coupon;
import com.betstat.backend.business.model.coupon.Utente;
import com.betstat.backend.business.model.response.ModelResponse;
import com.betstat.backend.business.model.response.OKResponse;
import com.betstat.backend.business.service.serviceCoupon.ServicesCoupon;
import com.betstat.backend.business.service.serviceCouponDao.ServiceCouponDao;
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

	@Autowired
	ServiceCouponDao serviceCouponDao;

	@Autowired
	ServicesCoupon serviceCoupon;

	@GetMapping(value = "/readcoupon")
	public void readcouponController() {
		logger.info("START readcouponController");
		// mokk Utente
		Utente utente = new Utente();
		utente.setId_utente(1);
		// acquisisce un coupon dall'html
		ModelResponse modelResponse = serviceCoupon.readCouponFromHtml(filePath, fileName, utente);
		if (modelResponse instanceof OKResponse) {
			logger.info(modelResponse.getDescription());
			Coupon coupon = GsonUtilities.getCouponFromString(modelResponse.getDescription());
			logger.info(coupon.toString());
			ModelResponse modelresponseGetCoupon = serviceCouponDao.getCoupon(coupon.getId_coupon());
			if (modelresponseGetCoupon == null) {
				// bisogna inserire il coupon
				ModelResponse modelResponseInsertCoupon = serviceCouponDao.insertCoupon(coupon);
				if (modelResponseInsertCoupon instanceof OKResponse) {
					logger.info("coupon " + coupon.getId_coupon() + " inserito correttamente");
					serviceCouponDao.insertDettaglioCoupon(coupon);
				} else {
					logger.error(modelResponseInsertCoupon.getDescription());
				}
			} else {
				// bisogna aggiornare il coupon
				Coupon oldCoupon = GsonUtilities.getCouponFromString(modelresponseGetCoupon.getDescription());
				logger.info("old coupon :" + oldCoupon.toString());
				ModelResponse modelRistrCoupon = serviceCoupon.restructureCoupon(oldCoupon);
				if (modelRistrCoupon instanceof OKResponse) {
					Coupon oldCouponR = GsonUtilities.getCouponFromString(modelRistrCoupon.getDescription());
					logger.info("old couponR :" + oldCouponR.toString());
					serviceCouponDao.updateCoupon(oldCouponR, coupon);
				}

			}

		} else {
			logger.error(modelResponse.getDescription());
		}

		logger.info("END readcouponController");
	}

	@GetMapping(value = "/a")
	public void a() {

		int n = 99;

		while (true) {
			for (int k = 0; k <= n; k++) {
				System.out.println("ciclo :" + k);
				for (int i = n; i >= k; i--) {
					if (i == n) {
						stampa(k, i - 1, i);
					} else {
						int j = i;
						int pos = i;
						if (j == k) {
							stampa(k, (pos - 1), j);
						} else {
							while (j <= n) {
								stampa(k, (pos - 1), j);
								j++;
							}
						}
					}
				}
			}
		}

	}

	private void stampa(int prtenza, int indice, int numero) {
		String prec = "";
		for (int i = prtenza; i <= indice; i++) {
			prec = prec + " " + i + " ";
		}
		System.out.println(prec + " " + numero);
	}

}
