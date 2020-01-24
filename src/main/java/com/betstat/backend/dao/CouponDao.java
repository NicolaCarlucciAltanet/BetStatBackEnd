package com.betstat.backend.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
					boolean founded = false;
					Coupon coupon = new Coupon();
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);
					while (resultSet.next()) {
						founded = true;
						coupon.setId_coupon(resultSet.getString("id_coupon"));
						coupon.setData_coupon(resultSet.getTimestamp("data_coupon"));
						coupon.setTipo(new Tipo(resultSet.getInt("id_tipo"), UtilitiesConstant.UNDEFINED_STRING));
						coupon.setEsito(new Esito(resultSet.getInt("id_esito"), UtilitiesConstant.UNDEFINED_STRING));
						Utente utente = new Utente();
						utente.setId_utente(resultSet.getInt("id_utente"));
						coupon.setUtente(utente);
						coupon.setImporto(resultSet.getFloat("importo"));
						coupon.setVincita(resultSet.getFloat("vincita"));
					}
					if (founded) {
						logger.info("Coupon " + codCoupon + " rilevato");
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromCoupon(coupon));
						return oKResponse;
					} else {
						// nessun coupon
						return null;
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
	 * Inserisce un nuovo coupon nel database
	 * 
	 * @param coupon Coupon da inserire
	 * @return ModelResponse
	 */
	public ModelResponse InsertCopupon(Coupon coupon) {
		logger.info("Inserimento coupon :" + coupon.getId_coupon());
		Connection connection = getConnection();
		if (connection != null) {
			String query = "INSERT INTO coupon VALUES ('" + coupon.getId_coupon() + "','" + coupon.getData_coupon()
					+ "'," + coupon.getTipo().getId_tipo() + "," + coupon.getEsito().getId_esito() + ","
					+ coupon.getUtente().getId_utente() + "," + coupon.getImporto() + "," + coupon.getVincita() + ")";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);

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
	 * Modifica del coupon esistente
	 * 
	 * @param coupon Coupon da modificare
	 * @return ModelResponse
	 */
	public ModelResponse updateCoupon(Coupon coupon) {
		logger.info("upate coupon id :" + coupon.getId_coupon());
		Connection connection = getConnection();
		if (connection != null) {
			String query = " UPDATE coupon SET data_coupon ='" + coupon.getData_coupon() + "',id_tipo="
					+ coupon.getTipo().getId_tipo() + ",id_esito=" + coupon.getEsito().getId_esito() + ",id_utente="
					+ coupon.getUtente().getId_utente() + ",importo=" + coupon.getImporto() + ",vincita="
					+ coupon.getVincita() + " where id_coupon='" + coupon.getId_coupon() + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);
					logger.info("update coupon :" + coupon.getId_coupon() + " completato");
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
					int id_tipo = UtilitiesConstant.UNDEFINED_INT;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						id_tipo = resultSet.getInt("id_tipo");
					}
					if (id_tipo == UtilitiesConstant.UNDEFINED_INT) {
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
	 * Restituisce il ModelResponse di un tipo dato il suo id
	 * 
	 * @param id_Tipo
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getNomeTipo(int id_Tipo) {
		logger.info("Ricerca nome per il tipo :" + id_Tipo);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select nome_tipo from tipo where id_tipo = " + id_Tipo + "";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					String nome_tipo = UtilitiesConstant.UNDEFINED_STRING;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						nome_tipo = resultSet.getString("nome_tipo");
					}
					if (nome_tipo.equalsIgnoreCase(UtilitiesConstant.UNDEFINED_STRING)) {
						return null;
					} else {
						Tipo tipo = new Tipo(id_Tipo, nome_tipo);
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
					int id_esito = UtilitiesConstant.UNDEFINED_INT;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						id_esito = resultSet.getInt("id_esito");
					}
					if (id_esito == UtilitiesConstant.UNDEFINED_INT) {
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
	 * Restituisce il ModelResponse di un Esito dato il suo id
	 * 
	 * @param id_Esito
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getNomeEsito(int id_Esito) {
		logger.info("Ricerca nome per l'esito :" + id_Esito);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select nome_esito from esito where id_esito = '" + id_Esito + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					String nome_esito = UtilitiesConstant.UNDEFINED_STRING;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						nome_esito = resultSet.getString("nome_esito");
					}
					if (nome_esito == UtilitiesConstant.UNDEFINED_STRING) {
						return null;
					} else {
						Esito esito = new Esito(id_Esito, nome_esito);
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

	/**
	 * Resituisce l'utente dal db dato il suo id
	 * 
	 * @param id_utente
	 * @return ModelResponse delll'utente o null se non c'è
	 */
	public ModelResponse getUtente(int id_utente) {
		logger.info("Ricerca utente per l'id :" + id_utente);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select * from utente where id_utente = " + id_utente + "";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					int id = UtilitiesConstant.UNDEFINED_INT;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);
					Utente utente = new Utente();
					while (resultSet.next()) {
						id = resultSet.getInt("id_utente");
						utente.setId_utente(resultSet.getInt("id_utente"));
						utente.setNome_utente(resultSet.getString("nome_utente"));
						utente.setCognome_utente(resultSet.getString("cognome_utente"));
						utente.setCf_utente(resultSet.getString("cf_utente"));
						utente.setEmail_utente(resultSet.getString("email_utente"));
						utente.setTelefono_utente(resultSet.getString("telefono_utente"));
						utente.setToken_utente(resultSet.getString("token_utente"));
						utente.setToken_validate_utente(resultSet.getBoolean("token_validate_utente"));
						utente.setLast_access_utente(resultSet.getTimestamp("last_access_utente"));
					}
					if (id == UtilitiesConstant.UNDEFINED_INT) {
						return null;
					} else {
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromUtente(utente));
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
	 * Inserisce il dettaglio del coupon nel database
	 * 
	 * @param dettaglioCoupon
	 * @param id_coupon
	 * @return ModelResponse o null se ci sono errori
	 */
	public ModelResponse insertDettaglioCoupon(DettaglioCoupon dettaglioCoupon, String id_coupon) {
		logger.info("inserimento dettaglio coupon :" + dettaglioCoupon.toString());
		Connection connection = getConnection();
		if (connection != null) {
			String query = " INSERT INTO dettaglio_coupon (data_dettaglio_coupon,id_squadra_casa,id_squadra_ospite,id_coupon,id_evento,id_pronostico,quota,id_esito) "
					+ "VALUES ('" + dettaglioCoupon.getData_dettaglio_coupon() + "',"
					+ dettaglioCoupon.getSquadra_casa().getId_squadra() + ","
					+ dettaglioCoupon.getSquadra_ospite().getId_squadra() + ",'" + id_coupon + "',"
					+ dettaglioCoupon.getId_evento() + "," + dettaglioCoupon.getPronostico().getId_pronostico() + ","
					+ dettaglioCoupon.getQuota() + "," + dettaglioCoupon.getEsito().getId_esito() + ")";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);
					logger.info("inserimento dettaglio coupon completato");
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

	public ModelResponse getDettaglioCoupon(int id_coupon) {
		logger.info("Ricerca del dettaglio coupon per il coupon:" + id_coupon);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select * from dettaglio_Coupon where id_coupon = '" + id_coupon + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					boolean founded = false;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);
					List<DettaglioCoupon> listDettaglioCoupon = new ArrayList<DettaglioCoupon>();
					while (resultSet.next()) {
						DettaglioCoupon dettaglioCoupon = new DettaglioCoupon();
						founded = true;
						dettaglioCoupon.setId_dettaglio_coupon(resultSet.getInt("id_dettaglio_coupon"));
						dettaglioCoupon.setData_dettaglio_coupon(resultSet.getTimestamp("data_dettaglio_coupon"));
						Squadra squadraCasa = new Squadra();
						squadraCasa.setId_squadra(resultSet.getInt("id_squadra_casa"));
						dettaglioCoupon.setSquadra_casa(squadraCasa);
						Squadra squadraOspite = new Squadra();
						squadraOspite.setId_squadra(resultSet.getInt("id_squadra_ospite"));
						dettaglioCoupon.setSquadra_ospite(squadraOspite);
						dettaglioCoupon.setId_evento(resultSet.getInt("id_squadra_ospite"));
						Pronostico pronostico = new Pronostico();
						pronostico.setId_pronostico(resultSet.getInt("id_pronostico"));
						dettaglioCoupon.setPronostico(pronostico);
						dettaglioCoupon.setQuota(resultSet.getFloat("quota"));
						Esito esito = new Esito();
						esito.setId_esito(resultSet.getInt("id_esito"));
						dettaglioCoupon.setEsito(esito);
						listDettaglioCoupon.add(dettaglioCoupon);
					}
					if (!founded) {
						return null;
					} else {
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromListDettaglioCoupon(listDettaglioCoupon));
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
	 * Restituisce il ModelResponse di una Squadra dato il suo nome
	 * 
	 * @param nome_squadra
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getIdSquadra(String nome_squadra) {
		logger.info("Ricerca id per la squadra :" + nome_squadra);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select id_squadra from squadra where nome_squadra = '" + nome_squadra + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					int id_squadra = UtilitiesConstant.UNDEFINED_INT;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						id_squadra = resultSet.getInt("id_squadra");
					}
					if (id_squadra == UtilitiesConstant.UNDEFINED_INT) {
						return null;
					} else {
						Squadra squadra = new Squadra(id_squadra, nome_squadra);
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromSquadra(squadra));
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
	 * Restituisce il ModelResponse di una Squadra dato il suo id
	 * 
	 * @param id_Squadra
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getNomeSquadra(int id_Squadra) {
		logger.info("Ricerca nome per la squadra :" + id_Squadra);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select nome_squadra from squadra where id_squadra = " + id_Squadra + "";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					String nome_squadra = UtilitiesConstant.UNDEFINED_STRING;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						nome_squadra = resultSet.getString("nome_squadra");
					}
					if (nome_squadra == UtilitiesConstant.UNDEFINED_STRING) {
						return null;
					} else {
						Squadra squadra = new Squadra(id_Squadra, nome_squadra);
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromSquadra(squadra));
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
	 * Inserisce una nuova Squadra dato il suo nome
	 * 
	 * @param nome_squadra
	 * @return ModelResponse
	 */
	public ModelResponse insertSquadra(String nome_squadra) {
		logger.info("inserimento nuova squadra :" + nome_squadra);
		Connection connection = getConnection();
		if (connection != null) {
			String query = " INSERT INTO squadra (nome_squadra) VALUES ('" + nome_squadra + "')";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);
					logger.info("inserimento squadra :" + nome_squadra + " completato");
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
	 * Restituisce il ModelResponse di un Pronostico dato il suo nome
	 * 
	 * @param nome_pronostico
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getIdPronostico(String nome_pronostico) {
		logger.info("Ricerca id per il pronostico :" + nome_pronostico);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select id_pronostico from pronostico where nome_pronostico = '" + nome_pronostico + "'";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					int id_pronostico = UtilitiesConstant.UNDEFINED_INT;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						id_pronostico = resultSet.getInt("id_pronostico");
					}
					if (id_pronostico == UtilitiesConstant.UNDEFINED_INT) {
						return null;
					} else {
						Pronostico pronostico = new Pronostico(id_pronostico, nome_pronostico);
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromPronostico(pronostico));
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
	 * Restituisce il ModelResponse di un Pronostico dato il suo id
	 * 
	 * @param id_pronostico
	 * @return ModelResponse o null se non esiste
	 */
	public ModelResponse getNomePronostico(int id_pronostico) {
		logger.info("Ricerca nome per il pronostico :" + id_pronostico);
		Connection connection = getConnection();
		ResultSet resultSet = null;
		if (connection != null) {
			String query = " Select nome_pronostico from pronostico where id_pronostico = " + id_pronostico + "";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					String nome_pronostico = UtilitiesConstant.UNDEFINED_STRING;
					logger.info("Esecuzione della query :" + query);
					resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						nome_pronostico = resultSet.getString("nome_pronostico");
					}
					if (nome_pronostico == UtilitiesConstant.UNDEFINED_STRING) {
						return null;
					} else {
						Pronostico pronostico = new Pronostico(id_pronostico, nome_pronostico);
						OKResponse oKResponse = new OKResponse();
						oKResponse.setDescription(GsonUtilities.getStringFromPronostico(pronostico));
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
	 * Inserisce un nuovo Pronostico dato il suo nome
	 * 
	 * @param nome_pronostico
	 * @return ModelResponse
	 */
	public ModelResponse insertPronostico(String nome_pronostico) {
		logger.info("inserimento nuovo pronostico :" + nome_pronostico);
		Connection connection = getConnection();
		if (connection != null) {
			String query = " INSERT INTO pronostico (nome_pronostico) VALUES ('" + nome_pronostico + "')";
			Statement statement = ConfigDao.getStatement(connection);
			if (statement != null) {
				try {
					logger.info("Esecuzione della query :" + query);
					statement.executeUpdate(query);
					logger.info("inserimento pronostico :" + nome_pronostico + " completato");
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
