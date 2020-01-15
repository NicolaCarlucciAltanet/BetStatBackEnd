package com.betstat.backend.utilities;

import com.betstat.backend.business.model.response.ERRORResponse;

public class ExceptionMessage {

	/**
	 * Restituisce l'errore dell'eccezione in un oggetto di tipo ERROResponse
	 * 
	 * @param exception
	 * @return
	 */
	public static ERRORResponse getMessageExceptionModelResponse(Exception exception) {
		ERRORResponse eRRORResponse = new ERRORResponse();
		if (exception.getMessage() != null) {
			eRRORResponse.setDescription(exception.getMessage());
		} else {
			eRRORResponse.setDescription(exception.getCause().toString());
		}
		return eRRORResponse;
	}

	/**
	 * Restituisce l'errore dell'eccezione in una stringa
	 * 
	 * @param exception
	 * @return
	 */
	public static String getMessageException(Exception exception) {
		if (exception.getMessage() != null) {
			return exception.getMessage();
		} else {
			return exception.getCause().toString();
		}
	}

}
