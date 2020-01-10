package com.betstat.backend.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betstat.backend.controller.coupon.CouponController;
import com.betstat.backend.model.coupon.Coupon;
import com.betstat.backend.model.coupon.Esito;
import com.betstat.backend.model.coupon.Tipo;
import com.betstat.backend.utilities.DateUtilities;
import com.betstat.backend.utilities.JsoupUtilities;
import com.betstat.backend.utilities.UtilitiesConstantProperties;

@Component
public class ServicesCoupon {

	final static Logger logger = LogManager.getLogger(CouponController.class);

	@Value("${" + UtilitiesConstantProperties.COUPON_DATA_CLASS + "}")
	private String couponDataClass;

	@Value("${" + UtilitiesConstantProperties.COUPON_COD_COUPON_CLASS + "}")
	private String couponCodCouponClass;

	@Value("${" + UtilitiesConstantProperties.COUPON_TIPO_CLASS + "}")
	private String coupontTipo;

	public void readCouponFromHtml(String path, String nameFile) {
		logger.info("START readSourcePageHtml");
		StringBuilder stringBuilder = new StringBuilder();
		Coupon coupon = new Coupon();

		try {
			URL urlObject = new URL(path + File.separator + nameFile);
			URLConnection urlConnection = urlObject.openConnection();
			urlConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
				String inputLine;
				while ((inputLine = bufferedReader.readLine()) != null) {
					stringBuilder.append(inputLine);
				}
			}
		} catch (Exception exception) {

		}

		Document doc = JsoupUtilities.getDocumentElement(stringBuilder.toString());

		// DATA
		Date data_coupon = DateUtilities.elaborateDate(doc.getElementsByClass(couponDataClass).text());
		coupon.setData_coupon(data_coupon);

		// CODICE COUPON
		String cod_coupon = doc.getElementsByClass(couponCodCouponClass).text();
		coupon.setId_coupon(cod_coupon);

		// TIPO COUPON
		String tipo_coupon = doc.getElementsByClass(coupontTipo).get(0).text();
		coupon.setTipo(new Tipo("", tipo_coupon));

		// ESITO
		String esito_coupon = doc.getElementsByClass(coupontTipo).get(1).text();
		coupon.setEsito(new Esito("", esito_coupon));

		Elements elements = doc.getElementsByClass("event-container");

		String prec = "";
		for (Element element : elements) {
			if (!element.getElementsByClass("label event-name").isEmpty()) {
				prec = element.getElementsByClass("label event-name").text();
				System.out.println(element.getElementsByClass("label event-name").text() + " "
						+ element.getElementsByClass("match-selection").text());
			} else {
				System.out.println(prec + " " + element.getElementsByClass("match-selection").text());

			}

		}

//		String prova1 = doc.getElementsByClass("label event-name").get(0).text();
//		doc.getElementsByClass("label event-name").p

		System.out.println(coupon.toString());

//		OKResponse okResponse = new OKResponse();
////		if(okResponse instanceof OKResponse.class)
//		return okResponse;
	}
}
