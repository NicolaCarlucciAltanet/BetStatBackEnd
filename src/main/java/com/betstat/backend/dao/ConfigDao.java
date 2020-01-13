package com.betstat.backend.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.betstat.backend.model.response.ERRORResponse;
import com.betstat.backend.model.response.ModelResponse;
import com.betstat.backend.model.response.OKResponse;

@Component
public class ConfigDao {

	final static Logger logger = LogManager.getLogger(ConfigDao.class);

	public ModelResponse loadDrivers(String dbStringConnection) {
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

}
