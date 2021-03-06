package com.betstat.backend.business.service.serviceCoupon;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betstat.backend.business.model.coupon.Coupon;
import com.betstat.backend.business.model.coupon.DettaglioCoupon;
import com.betstat.backend.business.model.coupon.Esito;
import com.betstat.backend.business.model.coupon.Pronostico;
import com.betstat.backend.business.model.coupon.Squadra;
import com.betstat.backend.business.model.coupon.Tipo;
import com.betstat.backend.business.model.coupon.Utente;
import com.betstat.backend.business.model.response.ERRORResponse;
import com.betstat.backend.business.model.response.ModelResponse;
import com.betstat.backend.business.model.response.OKResponse;
import com.betstat.backend.business.service.serviceCouponDao.ServiceCouponDao;
import com.betstat.backend.controller.coupon.CouponController;
import com.betstat.backend.utilities.DateUtilities;
import com.betstat.backend.utilities.ExceptionMessage;
import com.betstat.backend.utilities.GsonUtilities;
import com.betstat.backend.utilities.JsoupUtilities;
import com.betstat.backend.utilities.UtilitiesConstant;
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

	@Value("${" + UtilitiesConstantProperties.COUPON_EVENT_CONTAINER + "}")
	private String couponEventContainer;

	@Value("${" + UtilitiesConstantProperties.COUPON_LABEL_EVENT_NAME + "}")
	private String couponLabelEventName;

	@Value("${" + UtilitiesConstantProperties.COUPON_MATCH_SELECTION + "}")
	private String coupontMatchSelection;

	@Value("${" + UtilitiesConstantProperties.COUPON_EVENT_DATE + "}")
	private String coupontEventDate;

	@Value("${" + UtilitiesConstantProperties.COUPON_MARKET_NAME + "}")
	private String couponMarketName;

	@Value("${" + UtilitiesConstantProperties.COUPON_RESULT_CLASS + "}")
	private String couponResultClass;

	@Value("${" + UtilitiesConstantProperties.COUPON_AMOUNT + "}")
	private String couponAmount;

	@Autowired
	ServiceCouponDao serviceCouponDao;

	public ModelResponse readCouponFromHtml(String path, String nameFile, Utente utente) {
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
				Document doc = JsoupUtilities.getDocumentElement(stringBuilder.toString());

				// UTENTE
				coupon.setUtente(utente);

				// DATA
				Timestamp data_coupon = DateUtilities.elaborateDate(doc.getElementsByClass(couponDataClass).text());
				coupon.setData_coupon(data_coupon);

				// CODICE COUPON
				String cod_coupon = doc.getElementsByClass(couponCodCouponClass).text();
				coupon.setId_coupon(cod_coupon);

				// TIPO COUPON
				String tipo_coupon = doc.getElementsByClass(coupontTipo).get(0).text();
				coupon.setTipo(new Tipo(UtilitiesConstant.UNDEFINED_INT, tipo_coupon));
				float vincitaFloat = 0;
				if (tipo_coupon.equalsIgnoreCase(UtilitiesConstant.MULTIPLA)) {
					String amount = doc.getElementsByClass(couponAmount).text();
					vincitaFloat = Float.parseFloat(amount.replaceAll("EUR", "").replaceAll(",", "."));
				}

				try {
					// ESITO
					String esito_coupon = doc.getElementsByClass(coupontTipo).get(1).text()
							.replaceAll("fiber_manual_record", "").replaceAll(" ", "");
					if (esito_coupon.equalsIgnoreCase(UtilitiesConstant.INCORSONOWHITESPACE)) {
						esito_coupon = "In corso";
					}
					if (esito_coupon.equalsIgnoreCase(UtilitiesConstant.VINCENTE)) {
						coupon.setVincita(vincitaFloat);
					}
					coupon.setEsito(new Esito(UtilitiesConstant.UNDEFINED_INT, esito_coupon));
					float importo = 0;
					// IMPORTO
					if (tipo_coupon.equalsIgnoreCase(UtilitiesConstant.MULTIPLA)) {
						importo = Float.parseFloat(
								doc.getElementsByClass(coupontTipo).get(2).text().replaceAll("&nbsp;EUR", "")
										.replaceAll("EUR", "").replaceAll(",", ".").replaceAll("€", ""));
					} else {
						importo = Float.parseFloat(doc.getElementsByClass(coupontTipo).get(3).text()
								.replaceAll("&nbsp;EUR", "").replaceAll("EUR", "").replaceAll(",", "."));
					}

					coupon.setImporto(importo);
				} catch (Exception exception) {
					// ESITO
					String esito_coupon = doc.getElementsByClass("data-value tipoc ng-star-inserted").get(0).text()
							.replaceAll("fiber_manual_record", "").replaceAll(" ", "");
					if (esito_coupon.equalsIgnoreCase(UtilitiesConstant.INCORSONOWHITESPACE)) {
						esito_coupon = "In corso";
					}
					if (esito_coupon.equalsIgnoreCase(UtilitiesConstant.VINCENTE)) {
						coupon.setVincita(vincitaFloat);
					}
					coupon.setEsito(new Esito(UtilitiesConstant.UNDEFINED_INT, esito_coupon));

					// IMPORTO
					float importo = Float.parseFloat(doc.getElementsByClass(coupontTipo).get(2).text()
							.replaceAll("&nbsp;EUR", "").replaceAll("EUR", "").replaceAll(",", "."));
					coupon.setImporto(importo);
				}

				Elements elements = doc.getElementsByClass(couponEventContainer);

				DettaglioCoupon dettaglioCouponPrec = new DettaglioCoupon();

				List<DettaglioCoupon> listDettaglioCoupon = new ArrayList<DettaglioCoupon>();

				for (Element element : elements) {
					if (!element.getElementsByClass(couponLabelEventName).isEmpty()) {
						dettaglioCouponPrec = separateCod(element.getElementsByClass(couponLabelEventName).text());
						String pronostico = element.getElementsByClass(coupontMatchSelection).text();
						pronostico = getPronosticoFromLabel(element.getElementsByClass(couponMarketName).text(),
								pronostico);
						dettaglioCouponPrec.setPronostico(new Pronostico(UtilitiesConstant.UNDEFINED_INT, pronostico));
						dettaglioCouponPrec.setData_dettaglio_coupon(
								DateUtilities.elaborateDate(element.getElementsByClass(coupontEventDate).text()));
						dettaglioCouponPrec.setQuota(getQuota(element.getElementsByClass(couponMarketName).text()));
						String esito = getResult(element.getElementsByClass(couponResultClass).attr("style"));
						dettaglioCouponPrec.setEsito(new Esito(UtilitiesConstant.UNDEFINED_INT, esito));
						listDettaglioCoupon.add(dettaglioCouponPrec);
					} else {
						String pronostico = element.getElementsByClass(coupontMatchSelection).text();
						pronostico = getPronosticoFromLabel(element.getElementsByClass(couponMarketName).text(),
								pronostico);

						DettaglioCoupon dettaglioCoupon = new DettaglioCoupon();
						dettaglioCoupon.setSquadra_casa(dettaglioCouponPrec.getSquadra_casa());
						dettaglioCoupon.setSquadra_ospite(dettaglioCouponPrec.getSquadra_ospite());
						dettaglioCoupon.setData_dettaglio_coupon(dettaglioCouponPrec.getData_dettaglio_coupon());
						dettaglioCoupon.setId_evento(dettaglioCouponPrec.getId_evento());
						dettaglioCoupon.setPronostico(new Pronostico(UtilitiesConstant.UNDEFINED_INT, pronostico));
						dettaglioCoupon.setQuota(getQuota(element.getElementsByClass(couponMarketName).text()));
						String esito = getResult(element.getElementsByClass(couponResultClass).attr("style"));
						dettaglioCoupon.setEsito(new Esito(UtilitiesConstant.UNDEFINED_INT, esito));
						listDettaglioCoupon.add(dettaglioCoupon);
					}
				}
				coupon.setListDettaglioCoupon(listDettaglioCoupon);
				OKResponse okResponse = new OKResponse();
				okResponse.setDescription(GsonUtilities.getStringFromCoupon(coupon));
				return okResponse;
			}
		} catch (Exception exception) {
			ERRORResponse errorResponse = new ERRORResponse();
			if (exception.getMessage() != null) {
				errorResponse.setDescription(exception.getMessage());
			} else {
				errorResponse.setDescription(exception.getCause().toString());
			}

			return errorResponse;
		}

	}

	/**
	 * Separa il codice dalla squadra e crea il Dettaglio Coupon
	 * 
	 * @param input stringa Codice Squadra - squadra
	 * @return DettaglioCoupon
	 */
	private DettaglioCoupon separateCod(String input) {
		DettaglioCoupon dettaglioCoupon = new DettaglioCoupon();
		// cerca lo spazio bianco che separa il codice dalla squadra di casa
		int positionWhiteSpace = input.indexOf(" ");
		String cod_event = input.substring(0, positionWhiteSpace);
		cod_event = cod_event.replaceAll("\\s+", "");
		dettaglioCoupon.setId_evento(Integer.parseInt(cod_event));
		String squadreNoCodEvent = input.substring(positionWhiteSpace, input.length());
		dettaglioCoupon.setSquadra_casa(
				new Squadra(UtilitiesConstant.UNDEFINED_INT, squadreNoCodEvent.split(" -")[0].substring(1,squadreNoCodEvent.split(" -")[0].length())));
		dettaglioCoupon.setSquadra_ospite(
				new Squadra(UtilitiesConstant.UNDEFINED_INT, squadreNoCodEvent.split("- ")[1]));
		return dettaglioCoupon;
	}

	/**
	 * Acquisisce la quota dalla stringa
	 *
	 * @param input [q 1,73] GG
	 * @return quota
	 */
	private float getQuota(String input) {
		String quotaString = input.split("]")[0].replaceAll("\\[q ", "").replaceAll(",", ".");
		return Float.parseFloat(quotaString);
	}

	private String getResult(String result) {
		switch (result) {
		case UtilitiesConstant.VINCENTE_STYLE: {
			return UtilitiesConstant.VINCENTE;
		}
		case UtilitiesConstant.PERDENTE_STYLE: {
			return UtilitiesConstant.PERDENTE;
		}
		case UtilitiesConstant.INCORSO_STYLE: {
			return UtilitiesConstant.INCORSO;
		}
		default: {
			return UtilitiesConstant.INCORSO;
		}
		}
	}

	/**
	 * Ristruttura il coupon in seguito alla get dal database.
	 * 
	 * @param coupon Coupon da ristrutturare
	 * @return ModelResponse con il coupon ristrutturato o con l'errore.
	 */
	public ModelResponse restructureCoupon(Coupon coupon) {
		logger.info("START restructureCoupon id : " + coupon.getId_coupon());
		ModelResponse modelReponse;
		try {
			modelReponse = new OKResponse();
			coupon.setEsito(GsonUtilities
					.getEsitoFromString(serviceCouponDao.getEsito(coupon.getEsito().getId_esito()).getDescription()));
			coupon.setTipo(GsonUtilities
					.getTipoFromString(serviceCouponDao.getTipo(coupon.getTipo().getId_tipo()).getDescription()));
			modelReponse.setDescription(GsonUtilities.getStringFromCoupon(coupon));
			return modelReponse;
		} catch (Exception exception) {
			return ExceptionMessage.getMessageExceptionModelResponse(exception);
		}
	}

	/**
	 * Ristruttura il dettaglio Coupon in seguito alla get dal database
	 * 
	 * @param coupon
	 * @return ModelResponse
	 */
	public ModelResponse restructureDettaglioCoupon(Coupon coupon) {
		logger.info("START restructureDettaglioCoupon id : " + coupon.getId_coupon());
		List<DettaglioCoupon> oldLIstDettaglioCoupon = coupon.getListDettaglioCoupon();
		List<DettaglioCoupon> newLIstDettaglioCoupon = new ArrayList<DettaglioCoupon>();
		ModelResponse modelResponse;
		try {
			for (DettaglioCoupon dettaglioCoupon : oldLIstDettaglioCoupon) {
				dettaglioCoupon.setSquadra_casa(GsonUtilities.getSquadraFromString(serviceCouponDao
						.getSquadra(dettaglioCoupon.getSquadra_casa().getId_squadra()).getDescription()));
				dettaglioCoupon.setSquadra_ospite(GsonUtilities.getSquadraFromString(serviceCouponDao
						.getSquadra(dettaglioCoupon.getSquadra_ospite().getId_squadra()).getDescription()));
				dettaglioCoupon.setPronostico(GsonUtilities.getPronosticoFromString(serviceCouponDao
						.getPronostico(dettaglioCoupon.getPronostico().getId_pronostico()).getDescription()));
				dettaglioCoupon.setEsito(GsonUtilities.getEsitoFromString(
						serviceCouponDao.getEsito(dettaglioCoupon.getEsito().getId_esito()).getDescription()));
				newLIstDettaglioCoupon.add(dettaglioCoupon);
			}
			modelResponse = new OKResponse();
			modelResponse.setDescription(GsonUtilities.getStringFromListDettaglioCoupon(newLIstDettaglioCoupon));
			return modelResponse;
		} catch (Exception exception) {
			return ExceptionMessage.getMessageExceptionModelResponse(exception);
		}
	}

	private String getPronosticoFromLabel(String family, String result) {
		String pronosticoNoQNpP = "";

		String pronosticoNoQ = family.split("] ")[1];
		String pronosticoFinale = "";
		if (pronosticoNoQ.contains("(")) {
			pronosticoNoQNpP = pronosticoNoQ.split(" \\(")[0];
		}
		switch (pronosticoNoQNpP) {
		case UtilitiesConstant.OVERUNDER1X2: { // 1X2 + over/under
			pronosticoFinale = result + " " + pronosticoNoQ.split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER1TOSPITE: { // over/under 1° t ospite
			pronosticoFinale = pronosticoNoQ.split("O/U ")[1].split("\\(")[0] + result + " "
					+ pronosticoNoQ.split("O/U ")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER1TCASA: { // over/under 1° t casa
			pronosticoFinale = pronosticoNoQ.split("O/U ")[1].split("\\(")[0] + result + " "
					+ pronosticoNoQ.split("O/U ")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER2TOSPITE: { // over/under 2° t ospite
			pronosticoFinale = pronosticoNoQ.split("O/U ")[1].split("\\(")[0] + result + " "
					+ pronosticoNoQ.split("O/U ")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER2TCASA: { // over/under 2° t casa
			pronosticoFinale = pronosticoNoQ.split("O/U ")[1].split("\\(")[0] + result + " "
					+ pronosticoNoQ.split("O/U ")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER1T: { // over/under 1°t
			pronosticoFinale = result + " " + pronosticoNoQ.split("Over/Under")[1].split("\\(")[1].split("\\)")[0]
					+ pronosticoNoQ.split("Over/Under")[1].split("\\(")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER2T: { // over/under 2°t
			pronosticoFinale = result + " " + pronosticoNoQ.split("Over/Under")[1].split("\\(")[1].split("\\)")[0]
					+ pronosticoNoQ.split("Over/Under")[1].split("\\(")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDERCASA: { // over/under casa
			pronosticoFinale = pronosticoNoQ.split("O/U ")[1].split("\\(")[0] + result + " "
					+ pronosticoNoQ.split("O/U")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDEROSPITE: { // over/under casa
			pronosticoFinale = pronosticoNoQ.split("O/U ")[1].split("\\(")[0] + result + " "
					+ pronosticoNoQ.split("O/U")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER: { // over/under
			pronosticoFinale = result + " " + pronosticoNoQ.split("Over/Under ")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		case UtilitiesConstant.OVERUNDER1X2DOPPIACHANCE: { // 1x2 OVER/UNDER doppia chance
			pronosticoFinale = result + " " + pronosticoNoQ.split("O/U")[1].split("\\(")[1].split("\\)")[0];
		}
			break;
		default: {
			pronosticoFinale = result;
		}
		}

		return pronosticoFinale;

	}

}
