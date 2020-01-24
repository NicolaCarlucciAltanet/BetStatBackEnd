package com.betstat.backend.business.service.serviceCouponDao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betstat.backend.business.model.coupon.Coupon;
import com.betstat.backend.business.model.coupon.DettaglioCoupon;
import com.betstat.backend.business.model.coupon.Esito;
import com.betstat.backend.business.model.coupon.Pronostico;
import com.betstat.backend.business.model.coupon.Squadra;
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

	/**
	 * Chiama il servizio dao couponDao.getCopupon per cercare il coupon con id
	 * id_coupon
	 * 
	 * @param id_coupon
	 * @return null se il coupon non esiste
	 */
	public ModelResponse getCoupon(String id_coupon) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getCopupon(id_coupon);
		if (modelResonse != null) {
			return modelResonse;
		} else {
			// nessun coupon bisogna inserirlo
			logger.info("nessun coupon restituito con il codice :" + id_coupon);
			return null;
		}
	}

	public ModelResponse insertCoupon(Coupon coupon) {
		logger.info(" ServiceCouponDao Inserimento del coupon : " + coupon.getId_coupon());
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		boolean errors = false;
		String errorString = "";
		Tipo tipo = coupon.getTipo();
		if (tipo.getId_tipo() == UtilitiesConstant.UNDEFINED_INT) {
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
		if (esito.getId_esito() == UtilitiesConstant.UNDEFINED_INT) {
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
				logger.info("nessun esito restituito con il nome :" + esito.getNome_esito());
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
	 * Modifica di un coupon esistente
	 * 
	 * @param oldCoupon
	 * @param newCoupon
	 * @return ModelResponse
	 */
	public ModelResponse updateCoupon(Coupon oldCoupon, Coupon newCoupon) {
		logger.info(" ServiceCouponDao update del coupon old: " + oldCoupon.getId_coupon() + "new :"
				+ newCoupon.getId_coupon());
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		oldCoupon.setData_coupon(newCoupon.getData_coupon());
		oldCoupon
				.setTipo(GsonUtilities.getTipoFromString(getTipo(newCoupon.getTipo().getNome_tipo()).getDescription()));
		oldCoupon.setEsito(
				GsonUtilities.getEsitoFromString(getEsito(newCoupon.getEsito().getNome_esito()).getDescription()));
		oldCoupon.setUtente(newCoupon.getUtente());
		oldCoupon.setImporto(newCoupon.getImporto());
		oldCoupon.setVincita(newCoupon.getVincita());
		ModelResponse modelResponse = couponDao.updateCoupon(oldCoupon);
		return modelResponse;
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
	 * Restituisce il Tipo dato il id
	 * 
	 * @param nome_tipo
	 * @return ModelResponse
	 */
	public ModelResponse getTipo(int id_tipo) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getNomeTipo(id_tipo);
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
	 * Restituisce l'esito dato il suo nome
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
	 * Restituisce l'esito dato il suo id
	 * 
	 * @param nome_esito
	 * @return ModelResponse
	 */
	public ModelResponse getEsito(int id_esito) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getNomeEsito(id_esito);
		return modelResonse;
	}

	/**
	 * Inserisce un nuovo Esito dato il suo nome
	 * 
	 * @param nome_esito
	 * @return ModelResponse
	 */
	public ModelResponse writeNewEsito(String nome_esito) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.insertEsito(nome_esito);
		return modelResonse;
	}

	/**
	 * Restituisce la squadra dato il suo nome
	 * 
	 * @param nome_squadra
	 * @return ModelResponse
	 */
	public ModelResponse getSquadra(String nome_squadra) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getIdSquadra(nome_squadra);
		return modelResonse;
	}

	/**
	 * Restituisce la squadra dato il suo id
	 * 
	 * @param id_squadra
	 * @return ModelResponse
	 */
	public ModelResponse getSquadra(int id_squadra) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getNomeSquadra(id_squadra);
		return modelResonse;
	}

	/**
	 * Inserisce una nuova Squadra dato il suo nome
	 * 
	 * @param nome_squadra
	 * @return ModelResponse
	 */
	public ModelResponse writeNewSquadra(String nome_squadra) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.insertSquadra(nome_squadra);
		return modelResonse;
	}

	/**
	 * Restituisce il Pronostico dato il suo nome
	 * 
	 * @param nome_pronostico
	 * @return ModelResponse
	 */
	public ModelResponse getPronostico(String nome_pronostico) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getIdPronostico(nome_pronostico);
		return modelResonse;
	}

	/**
	 * Restituisce il Pronostico dato il suo id
	 * 
	 * @param id_squadra
	 * @return ModelResponse
	 */
	public ModelResponse getPronostico(int id_pronostico) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.getNomePronostico(id_pronostico);
		return modelResonse;
	}

	/**
	 * Inserisce un nuovo Pronostico dato il suo nome
	 * 
	 * @param nome_pronostico
	 * @return ModelResponse
	 */
	public ModelResponse writeNewPronostico(String nome_pronostico) {
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		ModelResponse modelResonse = couponDao.insertPronostico(nome_pronostico);
		return modelResonse;
	}

	public ModelResponse insertDettaglioCoupon(Coupon coupon) {
		logger.info(" insertDettaglioCoupon : " + coupon.getId_coupon());
		CouponDao couponDao = new CouponDao(dbUrl, dbRoot, dbPassword, dbStringConnection);
		boolean errors = false;
		String errorString = "";
		boolean errorsGlobal = false;
		String errorsStringGlobal = "";
		List<DettaglioCoupon> listDettaglioCoupon = coupon.getListDettaglioCoupon();
		for (DettaglioCoupon dettaglioCoupon : listDettaglioCoupon) {
			if (dettaglioCoupon.getSquadra_casa().getId_squadra() == UtilitiesConstant.UNDEFINED_INT) {
				ModelResponse modelResponse = getSquadra(dettaglioCoupon.getSquadra_casa().getNome_squadra());
				if (modelResponse != null) {
					if (modelResponse instanceof OKResponse) {
						Squadra squadra_casa = GsonUtilities.getSquadraFromString(modelResponse.getDescription());
						dettaglioCoupon.setSquadra_casa(squadra_casa);
					} else {
						errors = true;
						errorString = modelResponse.getDescription();
					}
				} else {
					// inserirlo
					logger.info("nessuna squadra restituita con il nome :"
							+ dettaglioCoupon.getSquadra_casa().getNome_squadra());
					ModelResponse modelWriteSquadra = writeNewSquadra(
							dettaglioCoupon.getSquadra_casa().getNome_squadra());
					if (modelWriteSquadra instanceof OKResponse) {
						ModelResponse newSquadraResponse = getSquadra(
								dettaglioCoupon.getSquadra_casa().getNome_squadra());
						Squadra newSquadraCasa = GsonUtilities
								.getSquadraFromString(newSquadraResponse.getDescription());
						dettaglioCoupon.setSquadra_casa(newSquadraCasa);
						System.out.println(newSquadraCasa.toString());
					} else {
						errors = true;
						errorString = modelWriteSquadra.getDescription();
					}
				}
			}
			if (dettaglioCoupon.getSquadra_ospite().getId_squadra() == UtilitiesConstant.UNDEFINED_INT) {
				ModelResponse modelResponseOspite = getSquadra(dettaglioCoupon.getSquadra_ospite().getNome_squadra());
				if (modelResponseOspite != null) {
					if (modelResponseOspite instanceof OKResponse) {
						Squadra squadra_ospite = GsonUtilities
								.getSquadraFromString(modelResponseOspite.getDescription());
						dettaglioCoupon.setSquadra_ospite(squadra_ospite);
					} else {
						errors = true;
						errorString = modelResponseOspite.getDescription();
					}
				} else {
					// inserirlo
					logger.info("nessuna squadra restituita con il nome :"
							+ dettaglioCoupon.getSquadra_ospite().getNome_squadra());
					ModelResponse modelWriteSquadraOsp = writeNewSquadra(
							dettaglioCoupon.getSquadra_ospite().getNome_squadra());
					if (modelWriteSquadraOsp instanceof OKResponse) {
						ModelResponse newSquadraOspResponse = getSquadra(
								dettaglioCoupon.getSquadra_ospite().getNome_squadra());
						Squadra newSquadraOspite = GsonUtilities
								.getSquadraFromString(newSquadraOspResponse.getDescription());
						dettaglioCoupon.setSquadra_ospite(newSquadraOspite);
						System.out.println(newSquadraOspite.toString());
					} else {
						errors = true;
						errorString = modelWriteSquadraOsp.getDescription();
					}
				}
			}

			if (dettaglioCoupon.getPronostico().getId_pronostico() == UtilitiesConstant.UNDEFINED_INT) {
				ModelResponse modelResponsePronostico = getPronostico(
						dettaglioCoupon.getPronostico().getNome_pronostico());
				if (modelResponsePronostico != null) {
					if (modelResponsePronostico instanceof OKResponse) {
						Pronostico pronostico = GsonUtilities
								.getPronosticoFromString(modelResponsePronostico.getDescription());
						dettaglioCoupon.setPronostico(pronostico);
					} else {
						errors = true;
						errorString = modelResponsePronostico.getDescription();
					}
				} else {
					// inserirlo
					logger.info("nessuna pronostico restituito con il nome :"
							+ dettaglioCoupon.getPronostico().getNome_pronostico());
					ModelResponse modelWritePronostico = writeNewPronostico(
							dettaglioCoupon.getPronostico().getNome_pronostico());
					if (modelWritePronostico instanceof OKResponse) {
						ModelResponse newPronosticoResponse = getPronostico(
								dettaglioCoupon.getPronostico().getNome_pronostico());
						Pronostico newPronostico = GsonUtilities
								.getPronosticoFromString(newPronosticoResponse.getDescription());
						dettaglioCoupon.setPronostico(newPronostico);
						System.out.println(newPronostico.toString());
					} else {
						errors = true;
						errorString = modelWritePronostico.getDescription();
					}
				}
			}
			Esito esito = dettaglioCoupon.getEsito();
			if (esito.getId_esito() == UtilitiesConstant.UNDEFINED_INT) {
				ModelResponse modelResonse = getEsito(esito.getNome_esito());
				if (modelResonse != null) {
					if (modelResonse instanceof OKResponse) {
						ModelResponse newEsitoResponse = getEsito(esito.getNome_esito());
						Esito newEsito = GsonUtilities.getEsitoFromString(newEsitoResponse.getDescription());
						dettaglioCoupon.setEsito(newEsito);
						System.out.println(newEsito.toString());
					} else {
						errors = true;
						errorString = modelResonse.getDescription();
					}
				} else {
					// nessun tipo, inserirlo
					logger.info("nessun esito restituito con il nome :" + esito.getNome_esito());
					ModelResponse modelWriteEsito = writeNewEsito(esito.getNome_esito());
					if (modelWriteEsito instanceof OKResponse) {
						ModelResponse newEsitoResponse = getEsito(esito.getNome_esito());
						Esito newEsito = GsonUtilities.getEsitoFromString(newEsitoResponse.getDescription());
						dettaglioCoupon.setEsito(newEsito);
						System.out.println(newEsito.toString());
					} else {
						errors = true;
						errorString = modelWriteEsito.getDescription();
					}
				}
			}
			if (!errors) {
				ModelResponse writeDettaglioCoupon = couponDao.insertDettaglioCoupon(dettaglioCoupon,
						coupon.getId_coupon());
				if (writeDettaglioCoupon instanceof ERRORResponse) {
					errorsGlobal = true;
					errorsStringGlobal = errorString;
				}
			} else {
				logger.error(errorString);
				errorsGlobal = true;
				errorsStringGlobal = errorString;
			}

		} // for

		if (errorsGlobal) {
			// se ci sono errori
			ERRORResponse errorResponse = new ERRORResponse();
			errorResponse.setDescription(errorsStringGlobal);
			return errorResponse;
		} else {
			return new OKResponse();
		}

	}

}
