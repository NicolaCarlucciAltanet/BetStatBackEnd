package com.betstat.backend.model.coupon;

import java.util.Date;

public class Coupon {

	private String id_coupon;

	private Date data_coupon;

	private Tipo tipo;

	private Esito esito;

	private Utente utente;

	private String importo;

	private String vincita;

	public Coupon() {

	}

	public Coupon(String id_coupon, Date data_coupon, Tipo tipo, Esito esito, Utente utente, String importo,
			String vincita) {
		this.id_coupon = id_coupon;
		this.data_coupon = data_coupon;
		this.tipo = tipo;
		this.esito = esito;
		this.utente = utente;
		this.importo = importo;
		this.vincita = vincita;
	}

	public String getId_coupon() {
		return id_coupon;
	}

	public void setId_coupon(String id_coupon) {
		this.id_coupon = id_coupon;
	}

	public Date getData_coupon() {
		return data_coupon;
	}

	public void setData_coupon(Date data_coupon) {
		this.data_coupon = data_coupon;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getVincita() {
		return vincita;
	}

	public void setVincita(String vincita) {
		this.vincita = vincita;
	}

}
