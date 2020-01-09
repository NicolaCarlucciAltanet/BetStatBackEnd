package com.betstat.backend.model.response;

import com.betstat.backend.utilities.UtilitiesConstant;

public class OKResponse extends ModelResponse{
	
	private final String type=UtilitiesConstant.OK_RESPONSE;
	
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
