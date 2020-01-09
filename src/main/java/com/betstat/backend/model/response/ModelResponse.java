package com.betstat.backend.model.response;

public abstract class ModelResponse {

	abstract String getType();
	
	abstract String getDescription();
	
	abstract void setDescription(String description);
}
