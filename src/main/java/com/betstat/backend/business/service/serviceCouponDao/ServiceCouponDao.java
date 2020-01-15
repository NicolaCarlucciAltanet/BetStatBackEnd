package com.betstat.backend.business.service.serviceCouponDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betstat.backend.business.model.coupon.Coupon;
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

	public void insertCoupon(Coupon coupon) {
		logger.info("Inserimento del coupon : " + coupon.getId_coupon());
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		boolean errors = false;
		String errorString = "";
		Tipo tipo = coupon.getTipo();
		if (tipo.getId_tipo() == UtilitiesConstant.UNDEFINED) {
			ModelResponse modelResonse = getTipo(tipo.getNome_tipo());
			if (modelResonse != null) {
				if(modelResonse instanceof OKResponse) {
					ModelResponse newTipoResponse = getTipo(tipo.getNome_tipo());
					Tipo newTipo = GsonUtilities.getTipoFromString(newTipoResponse.getDescription());
					coupon.setTipo(newTipo);
					System.out.println(newTipo.toString());
				}else {
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
	}

	public ModelResponse getTipo(String nome_tipo) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getIdTipo(nome_tipo);
		return modelResonse;
	}

	public ModelResponse writeNewTipo(String nome_tipo) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.insertTipo(nome_tipo);
		return modelResonse;
	}
}
