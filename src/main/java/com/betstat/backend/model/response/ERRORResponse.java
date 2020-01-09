package com.betstat.backend.model.response;

import com.betstat.backend.utilities.UtilitiesConstant;

public class ERRORResponse extends ModelResponse {
	
	private final String type = UtilitiesConstant.ERROR_RESPONSE;

	private String description;

	@Override
	String getType() {
		return type;
	}

	@Override
	String getDescription() {
		return description;
	}

	@Override
	void setDescription(String description) {
		this.description = description;
	}

}
