package com.betstat.backend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.betstat.backend.business.model.coupon.Coupon;
import com.betstat.backend.business.model.coupon.Esito;
import com.betstat.backend.business.model.coupon.Tipo;
import com.betstat.backend.business.model.response.ERRORResponse;
import com.betstat.backend.business.model.response.ModelResponse;
import com.betstat.backend.business.model.response.OKResponse;
import com.betstat.backend.utilities.DateUtilities;
import com.betstat.backend.utilities.ExceptionMessage;
import com.betstat.backend.utilities.GsonUtilities;
import com.betstat.backend.utilities.UtilitiesConstant;

/**
 * Classe che si occupa delle query coupon al database
 * 
 * @author Nicola
 *
 */
public class CouponDao {

	private String urlDb;

	private String user;

	private String password;

	private String drivers;

	final static Logger logger = LogManager.getLogger(CouponDao.class);

	public CouponDao(String urlDb, String user, String password, String drivers) {
		this.urlDb = urlDb;
		this.user = user;
		this.password = password;
		this.drivers = drivers;
		loadDriver();
	}

	private boolean loadDriver() {
		return ConfigDao.loadDrivers(drivers);
	}

	private Connection getConnection() {
		return ConfigDao.openConnection(urlDb, user, password);
	}

	/**
	 * Restituisce il coupon dato il codice coupon
	 * 
	 * @param codCoupon
	 * @return ModelResponse o null se non è presente alcun risultato
	 */
	public ModelResponse getCopupon(String codCoupon) {
		logger.info("Richiesta coupon :" + codCoupon);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select * from coupon where id_coupon = '" + codCoupon + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);
					if (resultSet.next() == false) {
						return null;
						// nessun coupon
					} else {
						OKResponse oKResponse = new OKResponse();
						return oKResponse;
					}
				} catch (SQLException sQLException) {
					ERRORResponse eRRORResponse = ExceptionMessage.getMessageExceptionModelResponse(sQLException);
					logger.error(eRRORResponse.getDescription());
					return eRRORResponse;
				} finally {
					ConfigDao.closeResultSetAndConnection(resultSet, statement, connection);
				}
			} else {
				ERRORResponse eRRORResponseSt = new ERRORResponse();
				eRRORResponseSt.setDescription("statement null");
				return eRRORResponseSt;
			}
		} else {
			ERRORResponse eRRORResponseC = new ERRORResponse();
			eRRORResponseC.setDescription("statement null");
			return eRRORResponseC;
		}
	}

	public ModelResponse InsertCopupon(Coupon coupon) {
		logger.info("Inserimento coupon :" + coupon.getId_coupon());
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = "INSERT INTO coupon VALUES ('" + coupon.getId_coupon() + "',"
					+coupon.getData_coupon() + ","
					+ coupon.getTipo().getId_tipo() + "," + coupon.getEsito().getId_esito() + "," + null + ","
					+ coupon.getImporto() + "," + coupon.getVincita() + ")";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);
					int i = 0;
					while (resultSet.next()) {
						i++;
					}
					if (i == 0) {
						return null;
						// nessun coupon

					} else {
						OKResponse oKResponse = new OKResponse();
						return oKResponse;
					}
				} catch (SQLException sQLException) {
					ERRORResponse eRRORResponse = ExceptionMessage.getMessageExceptionModelResponse(sQLException);
					logger.error(eRRORResponse.getDescription());
					return eRRORResponse;
				} finally {
					ConfigDao.closeResultSetAndConnection(resultSet, statement, connection);
				}
			} else {
				ERRORResponse eRRORResponseSt = new ERRORResponse();
				eRRORResponseSt.setDescription("statement null");
				return eRRORResponseSt;
			}
		} else {
			ERRORResponse eRRORResponseC = new ERRORResponse();
			eRRORResponseC.setDescription("statement null");
			return eRRORResponseC;
		}
	}

	/**
	 * Restituisce il ModelResponse di un tipo dato il suo nome
	 * 
	 * @param nomeTipo
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getIdTipo(String nomeTipo) {
		logger.info("Ricerca id per il tipo :" + nomeTipo);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select id_tipo from tipo where nome_tipo = '" + nomeTipo + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					int id_tipo = -1;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						id_tipo = resultSet.getInt("id_tipo");
					}
					if (id_tipo == UtilitiesConstant.UNDEFINED) {
						return null;
					} else {
						Tipo tipo = new Tipo(id_tipo, nomeTipo);
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromTipo(tipo));
						return oKResponse;
					}
				} catch (SQLException sQLException) {
					ERRORResponse eRRORResponse = ExceptionMessage.getMessageExceptionModelResponse(sQLException);
					logger.error(eRRORResponse.getDescription());
					return eRRORResponse;
				} finally {
					ConfigDao.closeResultSetAndConnection(resultSet, statement, connection);
				}
			} else {
				ERRORResponse eRRORResponseSt = new ERRORResponse();
				eRRORResponseSt.setDescription("statement null");
				return eRRORResponseSt;
			}
		} else {
			ERRORResponse eRRORResponseC = new ERRORResponse();
			eRRORResponseC.setDescription("statement null");
			return eRRORResponseC;
		}
	}

	/**
	 * Inserisce un nuovo tipo dato il suo nome
	 * 
	 * @param nomeTipo
	 * @return ModelResponse
	 */
	public ModelResponse insertTipo(String nomeTipo) {
		logger.info("inserimento nuovo tipo :" + nomeTipo);
		Connection connection = getConnection();
		if (connection != null) {
			String query = " INSERT INTO tipo (nome_tipo) VALUES ('" + nomeTipo + "')";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);
					logger.info("inserimento tipo :" + nomeTipo + " completato");
					OKResponse oKResponse = new OKResponse();
					return oKResponse;
				} catch (SQLException sQLException) {
					ERRORResponse eRRORResponse = ExceptionMessage.getMessageExceptionModelResponse(sQLException);
					logger.error(eRRORResponse.getDescription());
					return eRRORResponse;
				} finally {
					ConfigDao.closeResultSetAndConnection(statement, connection);
				}
			} else {
				ERRORResponse eRRORResponseSt = new ERRORResponse();
				eRRORResponseSt.setDescription("statement null");
				return eRRORResponseSt;
			}
		} else {
			ERRORResponse eRRORResponseC = new ERRORResponse();
			eRRORResponseC.setDescription("statement null");
			return eRRORResponseC;
		}
	}

	/**
	 * Restituisce il ModelResponse di un Esito dato il suo nome
	 * 
	 * @param nomeEsito
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getIdEsito(String nomeEsito) {
		logger.info("Ricerca id per l'esito :" + nomeEsito);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select id_esito from esito where nome_esito = '" + nomeEsito + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					int id_esito = UtilitiesConstant.UNDEFINED;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						id_esito = resultSet.getInt("id_esito");
					}
					if (id_esito == UtilitiesConstant.UNDEFINED) {
						return null;
					} else {
						Esito esito = new Esito(id_esito, nomeEsito);
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromTipo(esito));
						return oKResponse;
					}
				} catch (SQLException sQLException) {
					ERRORResponse eRRORResponse = ExceptionMessage.getMessageExceptionModelResponse(sQLException);
					logger.error(eRRORResponse.getDescription());
					return eRRORResponse;
				} finally {
					ConfigDao.closeResultSetAndConnection(resultSet, statement, connection);
				}
			} else {
				ERRORResponse eRRORResponseSt = new ERRORResponse();
				eRRORResponseSt.setDescription("statement null");
				return eRRORResponseSt;
			}
		} else {
			ERRORResponse eRRORResponseC = new ERRORResponse();
			eRRORResponseC.setDescription("statement null");
			return eRRORResponseC;
		}
	}

	/**
	 * Inserisce un nuovo Esito dato il suo nome
	 * 
	 * @param nomeTipo
	 * @return ModelResponse
	 */
	public ModelResponse insertEsito(String nomeEsito) {
		logger.info("inserimento nuovo esito :" + nomeEsito);
		Connection connection = getConnection();
		if (connection != null) {
			String query = " INSERT INTO esito (nome_esito) VALUES ('" + nomeEsito + "')";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);
					logger.info("inserimento esito :" + nomeEsito + " completato");
					OKResponse oKResponse = new OKResponse();
					return oKResponse;
				} catch (SQLException sQLException) {
					ERRORResponse eRRORResponse = ExceptionMessage.getMessageExceptionModelResponse(sQLException);
					logger.error(eRRORResponse.getDescription());
					return eRRORResponse;
				} finally {
					ConfigDao.closeResultSetAndConnection(statement, connection);
				}
			} else {
				ERRORResponse eRRORResponseSt = new ERRORResponse();
				eRRORResponseSt.setDescription("statement null");
				return eRRORResponseSt;
			}
		} else {
			ERRORResponse eRRORResponseC = new ERRORResponse();
			eRRORResponseC.setDescription("statement null");
			return eRRORResponseC;
		}
	}
}
