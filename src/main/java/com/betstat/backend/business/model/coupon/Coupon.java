package com.betstat.backend.business.model.coupon;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Coupon {

	private String id_coupon;

	private Timestamp data_coupon;

	private Tipo tipo;

	private Esito esito;

	private Utente utente;

	private float importo;

	private float vincita;

	private List<DettaglioCoupon> listDettaglioCoupon;

	public Coupon() {

	}

	public Coupon(String id_coupon, Timestamp data_coupon, Tipo tipo, Esito esito, Utente utente, float importo,
			float vincita, List<DettaglioCoupon> listDettaglioCoupon) {
		this.id_coupon = id_coupon;
		this.data_coupon = data_coupon;
		this.tipo = tipo;
		this.esito = esito;
		this.utente = utente;
		this.importo = importo;
		this.vincita = vincita;
		this.listDettaglioCoupon = listDettaglioCoupon;
	}

	public String getId_coupon() {
		return id_coupon;
	}

	public void setId_coupon(String id_coupon) {
		this.id_coupon = id_coupon;
	}

	public Timestamp getData_coupon() {
		return data_coupon;
	}

	public void setData_coupon(Timestamp data_coupon) {
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

	public float getImporto() {
		return importo;
	}

	public void setImporto(float importo) {
		this.importo = importo;
	}

	public float getVincita() {
		return vincita;
	}

	public void setVincita(float vincita) {
		this.vincita = vincita;
	}

	public List<DettaglioCoupon> getListDettaglioCoupon() {
		return listDettaglioCoupon;
	}

	public void setListDettaglioCoupon(List<DettaglioCoupon> listDettaglioCoupon) {
		this.listDettaglioCoupon = listDettaglioCoupon;
	}

	@Override
	public String toString() {
		return "Coupon [id_coupon=" + id_coupon + ", data_coupon=" + data_coupon + ", tipo=" + tipo + ", esito=" + esito
				+ ", utente=" + utente + ", importo=" + importo + ", vincita=" + vincita + ", listDettaglioCoupon="
				+ listDettaglioCoupon + "]";
	}

}
