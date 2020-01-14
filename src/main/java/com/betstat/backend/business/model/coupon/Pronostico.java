package com.betstat.backend.business.model.coupon;

public class Pronostico {

	private String id_pronostico;

	private String nome_pronostico;

	public Pronostico() {
	}

	public Pronostico(String id_pronostico, String nome_pronostico) {
		this.id_pronostico = id_pronostico;
		this.nome_pronostico = nome_pronostico;
	}

	public String getId_pronostico() {
		return id_pronostico;
	}

	public void setId_pronostico(String id_pronostico) {
		this.id_pronostico = id_pronostico;
	}

	public String getNome_pronostico() {
		return nome_pronostico;
	}

	public void setNome_pronostico(String nome_pronostico) {
		this.nome_pronostico = nome_pronostico;
	}

	@Override
	public String toString() {
		return "Pronostico [id_pronostico=" + id_pronostico + ", nome_pronostico=" + nome_pronostico + "]";
	}

}
