package com.betstat.backend.model.coupon;

public class Tipo {

	private String id_tipo;

	private String nome_tipo;

	public Tipo() {
	}

	public Tipo(String id_tipo, String nome_tipo) {
		this.id_tipo = id_tipo;
		this.nome_tipo = nome_tipo;
	}

	public String getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(String id_tipo) {
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
