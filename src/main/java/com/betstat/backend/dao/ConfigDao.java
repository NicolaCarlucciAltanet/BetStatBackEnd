package com.betstat.backend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.betstat.backend.business.model.response.ERRORResponse;
import com.betstat.backend.business.model.response.ModelResponse;
import com.betstat.backend.business.model.response.OKResponse;



@Component
public class ConfigDao {

	final static Logger logger = LogManager.getLogger(ConfigDao.class);

	public static ModelResponse loadDrivers(String dbStringConnection) {
		try {
			Class.forName(dbStringConnection);
			OKResponse okResponse = new OKResponse();
			return okResponse;
		} catch (ClassNotFoundException classNotFoundException) {
			ERRORResponse erroreResponse = new ERRORResponse();
			erroreResponse.setDescription(classNotFoundException.getMessage());
			return erroreResponse;
		}
	}

	public static Connection openConnection(String stringConnection, String user, String password) {
		try {
			logger.info("Connessione al database ...");
			Connection connection = DriverManager.getConnection(stringConnection, user, password);
			return connection;
		} catch (SQLException sQLException) {
			logger.error(sQLException);
			return null;
		}
	}

	public static ResultSet executeQuery(String query, Connection connection) {
		try {
			logger.info("executeQuery " + query);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return resultSet;
		} catch (SQLException sQLException) {
			logger.error(sQLException);
			return null;
		}
	}

	public static boolean closeResultSet(ResultSet resultSet, Statement statement, Connection connection) {
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

}
