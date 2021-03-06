package com.betstat.backend.business.model.coupon;

public class Esito {

	private int id_esito;

	private String nome_esito;

	public Esito() {
	}

	public Esito(int id_esito, String nome_esito) {
		this.id_esito = id_esito;
		this.nome_esito = nome_esito;
	}

	public int getId_esito() {
		return id_esito;
	}

	public void setId_esito(int id_esito) {
		this.id_esito = id_esito;
	}

	public String getNome_esito() {
		return nome_esito;
	}

	public void setNome_esito(String nome_esito) {
		this.nome_esito = nome_esito;
	}

	@Override
	public String toString() {
		return "Esito [id_esito=" + id_esito + ", nome_esito=" + nome_esito + "]";
	}

}
