package com.betstat.backend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.betstat.backend.utilities.ExceptionMessage;

/**
 * Classe che si occupa della connessione al database
 * 
 * @author Nicola
 *
 */
@Component
public class ConfigDao {

	final static Logger logger = LogManager.getLogger(ConfigDao.class);

	/**
	 * Caricamento dei driver del database
	 * 
	 * @param dbStringConnection stringa dei drivers
	 * @return ModelResponse
	 */
	public static boolean loadDrivers(String driversString) {
		logger.info("Caricamento dei driver del database...");
		try {
			Class.forName(driversString);
			logger.info("Driver caricati correttamente");
			return true;
		} catch (ClassNotFoundException classNotFoundException) {
			logger.info(ExceptionMessage.getMessageException(classNotFoundException));
			return false;
		}
	}

	/**
	 * Ritorna la connessione al database
	 * 
	 * @param stringConnection stringa di connessione al database
	 * @param user             utente
	 * @param password         password
	 * @return Connecton or null
	 */
	public static Connection openConnection(String stringConnection, String user, String password) {
		try {
			logger.info("Connessione al database");
			Connection connection = DriverManager.getConnection(stringConnection, user, password);
			logger.info("Connessione al database eseguita correttamente");
			return connection;
		} catch (SQLException sQLException) {
			logger.error(sQLException);
			return null;
		}
	}

	/**
	 * Ritorna lo statement
	 * 
	 * @param connection
	 * @return Statement or null
	 */
	public static Statement getStatement(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			return statement;
		} catch (SQLException sQLException) {
			logger.error(sQLException);
			return null;
		}
	}

	/**
	 * Chiude la connessione al database quando ci sono risultati
	 * 
	 * @param resultSet
	 * @param statement
	 * @param connection
	 * @return true se tutt ok, false altrimenti
	 */
	public static boolean closeResultSetAndConnection(ResultSet resultSet, Statement statement, Connection connection) {
		try {
			resultSet.close();
			statement.close();
			connection.close();
			return true;
		} catch (SQLException sQLException) {
			logger.error(sQLException);
			return false;
		}
	}
	
	/**
	 * Chiude la connessione al database quando non ci sono risultati
	 * 
	 * @param resultSet
	 * @param statement
	 * @param connection
	 * @return true se tutt ok, false altrimenti
	 */
	public static boolean closeResultSetAndConnection(Statement statement, Connection connection) {
		try {
			statement.close();
			connection.close();
			return true;
		} catch (SQLException sQLException) {
			logger.error(sQLException);
			return false;
		}
	}

}
