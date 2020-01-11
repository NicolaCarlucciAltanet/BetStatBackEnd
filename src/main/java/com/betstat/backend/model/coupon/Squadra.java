package com.betstat.backend.model.coupon;

public class Squadra {

	private String id_squadra;

	private String nome_squadra;

	public Squadra() {
	}

	public Squadra(String id_squadra, String nome_squadra) {
		this.id_squadra = id_squadra;
		this.nome_squadra = nome_squadra;
	}

	public String getId_squadra() {
		return id_squadra;
	}

	public void setId_squadra(String id_squadra) {
		this.id_squadra = id_squadra;
	}

	public String getNome_squadra() {
		return nome_squadra;
	}

	public void setNome_squadra(String nome_squadra) {
		this.nome_squadra = nome_squadra;
	}

	@Override
	public String toString() {
		return "Squadra [id_squadra=" + id_squadra + ", nome_squadra=" + nome_squadra + "]";
	}

}
