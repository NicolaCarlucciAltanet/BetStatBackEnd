package com.betstat.backend.business.service.serviceCouponDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betstat.backend.business.model.coupon.Coupon;
import com.betstat.backend.business.model.coupon.Esito;
import com.betstat.backend.business.model.coupon.Tipo;
import com.betstat.backend.business.model.response.ERRORResponse;
import com.betstat.backend.business.model.response.ModelResponse;
import com.betstat.backend.business.model.response.OKResponse;
import com.betstat.backend.dao.CouponDao;
import com.betstat.backend.utilities.GsonUtilities;
import com.betstat.backend.utilities.UtilitiesConstant;
import com.betstat.backend.utilities.UtilitiesConstantProperties;

@Component
public class ServiceCouponDao {

	@Value("${" + UtilitiesConstantProperties.DB_STRING_CONNECTION + "}")
	private String dbStringConnection;

	@Value("${" + UtilitiesConstantProperties.DB_URL + "}")
	private String dbUrl;

	@Value("${" + UtilitiesConstantProperties.DB_PASSWORD + "}")
	private String dbPassword;

	@Value("${" + UtilitiesConstantProperties.DB_ROOT + "}")
	private String dbRoot;

	final static Logger logger = LogManager.getLogger(ServiceCouponDao.class);

	public void getCoupon(String id_coupon) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getCopupon(id_coupon);
		if (modelResonse != null) {
			if (modelResonse instanceof ERRORResponse) {
				ERRORResponse eRRORResponse = (ERRORResponse) couponDao.getCopupon(id_coupon);
				logger.error(eRRORResponse.getDescription());
			}
		} else {
			// nessun coupon bisogna inserirlo
			logger.info("nessun coupon restituito con il codice :" + id_coupon);
		}
	}

	public ModelResponse insertCoupon(Coupon coupon) {
		logger.info("Inserimento del coupon : " + coupon.getId_coupon());
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		boolean errors = false;
		String errorString = "";
		Tipo tipo = coupon.getTipo();
		if (tipo.getId_tipo() == UtilitiesConstant.UNDEFINED) {
			ModelResponse modelResonse = getTipo(tipo.getNome_tipo());
			if (modelResonse != null) {
				if (modelResonse instanceof OKResponse) {
					ModelResponse newTipoResponse = getTipo(tipo.getNome_tipo());
					Tipo newTipo = GsonUtilities.getTipoFromString(newTipoResponse.getDescription());
					coupon.setTipo(newTipo);
					System.out.println(newTipo.toString());
				} else {
					errors = true;
					errorString = modelResonse.getDescription();
				}
			} else {
				// nessun tipo, inserirlo
				logger.info("nessun tipo restituito con il nome :" + tipo.getNome_tipo());
				ModelResponse modelWriteTipo = writeNewTipo(tipo.getNome_tipo());
				if (modelWriteTipo instanceof OKResponse) {
					ModelResponse newTipoResponse = getTipo(tipo.getNome_tipo());
					Tipo newTipo = GsonUtilities.getTipoFromString(newTipoResponse.getDescription());
					coupon.setTipo(newTipo);
					System.out.println(newTipo.toString());
				} else {
					errors = true;
					errorString = modelWriteTipo.getDescription();
				}
			}
		}

		Esito esito = coupon.getEsito();
		if (esito.getId_esito() == UtilitiesConstant.UNDEFINED) {
			ModelResponse modelResonse = getEsito(esito.getNome_esito());
			if (modelResonse != null) {
				if (modelResonse instanceof OKResponse) {
					ModelResponse newEsitoResponse = getEsito(esito.getNome_esito());
					Esito newEsito = GsonUtilities.getEsitoFromString(newEsitoResponse.getDescription());
					coupon.setEsito(newEsito);
					System.out.println(newEsito.toString());
				} else {
					errors = true;
					errorString = modelResonse.getDescription();
				}
			} else {
				// nessun tipo, inserirlo
				logger.info("nessun rsito restituito con il nome :" + esito.getNome_esito());
				ModelResponse modelWriteEsito = writeNewEsito(esito.getNome_esito());
				if (modelWriteEsito instanceof OKResponse) {
					ModelResponse newEsitoResponse = getEsito(esito.getNome_esito());
					Esito newEsito = GsonUtilities.getEsitoFromString(newEsitoResponse.getDescription());
					coupon.setEsito(newEsito);
					System.out.println(newEsito.toString());
				} else {
					errors = true;
					errorString = modelWriteEsito.getDescription();
				}
			}
		}
		if (errors) {
			ERRORResponse eRRorResponse = new ERRORResponse();
			eRRorResponse.setDescription(errorString);
			return eRRorResponse;
		} else {
			ModelResponse modelResponse = couponDao.InsertCopupon(coupon);
			return modelResponse;
		}
	}

	/**
	 * Restituisce il Tipo dato il suo nome
	 * 
	 * @param nome_tipo
	 * @return ModelResponse
	 */
	public ModelResponse getTipo(String nome_tipo) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getIdTipo(nome_tipo);
		return modelResonse;
	}

	/**
	 * Inserisce un nuovo tipo dato il suo nome
	 * 
	 * @param nome_tipo
	 * @return ModelResponse
	 */
	public ModelResponse writeNewTipo(String nome_tipo) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.insertTipo(nome_tipo);
		return modelResonse;
	}

	/**
	 * Restituisce il Tipo dato il suo nome
	 * 
	 * @param nome_esito
	 * @return ModelResponse
	 */
	public ModelResponse getEsito(String nome_esito) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getIdEsito(nome_esito);
		return modelResonse;
	}

	/**
	 * Inserisce un nuovo tipo dato il suo nome
	 * 
	 * @param nome_esito
	 * @return ModelResponse
	 */
	public ModelResponse writeNewEsito(String nome_esito) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.insertEsito(nome_esito);
		return modelResonse;
	}

}
