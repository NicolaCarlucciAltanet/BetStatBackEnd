package com.betstat.backend.business.model.response;

public abstract class ModelResponse {

	public abstract String getType();

	public abstract String getDescription();

	public abstract void setDescription(String description);
}
