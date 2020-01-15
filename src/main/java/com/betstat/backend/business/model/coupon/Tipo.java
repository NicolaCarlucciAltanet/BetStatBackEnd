package com.betstat.backend.business.model.coupon;

public class Tipo {

	private int id_tipo;

	private String nome_tipo;

	public Tipo() {
	}

	public Tipo(int id_tipo, String nome_tipo) {
		this.id_tipo = id_tipo;
		this.nome_tipo = nome_tipo;
	}

	public int getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getNome_tipo() {
		return nome_tipo;
	}

	public void setNome_tipo(String nome_tipo) {
		this.nome_tipo = nome_tipo;
	}

	@Override
	public String toString() {
		return "Tipo [id_tipo=" + id_tipo + ", nome_tipo=" + nome_tipo + "]";
	}

}
