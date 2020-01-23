package com.betstat.backend.business.model.coupon;

import java.sql.Timestamp;

public class DettaglioCoupon {

	private String id_dettaglio_coupon;

	private Squadra squadra_casa;

	private Squadra squadra_ospite;

	private Timestamp data_dettaglio_coupon;

	private String id_evento;

	private Pronostico pronostico;

	private float quota;

	private Esito esito;

	public DettaglioCoupon() {
	}

	public DettaglioCoupon(String id_dettaglio_coupon, Squadra squadra_casa, Squadra squadra_ospite,
			Timestamp data_dettaglio_coupon, String id_evento, Pronostico pronostico, float quota, Esito esito) {
		this.id_dettaglio_coupon = id_dettaglio_coupon;
		this.squadra_casa = squadra_casa;
		this.squadra_ospite = squadra_ospite;
		this.data_dettaglio_coupon = data_dettaglio_coupon;
		this.id_evento = id_evento;
		this.pronostico = pronostico;
		this.quota = quota;
		this.esito = esito;
	}

	public String getId_dettaglio_coupon() {
		return id_dettaglio_coupon;
	}

	public void setId_dettaglio_coupon(String id_dettaglio_coupon) {
		this.id_dettaglio_coupon = id_dettaglio_coupon;
	}

	public Squadra getSquadra_casa() {
		return squadra_casa;
	}

	public void setSquadra_casa(Squadra squadra_casa) {
		this.squadra_casa = squadra_casa;
	}

	public Squadra getSquadra_ospite() {
		return squadra_ospite;
	}

	public void setSquadra_ospite(Squadra squadra_ospite) {
		this.squadra_ospite = squadra_ospite;
	}

	public Timestamp getData_dettaglio_coupon() {
		return data_dettaglio_coupon;
	}

	public void setData_dettaglio_coupon(Timestamp data_dettaglio_coupon) {
		this.data_dettaglio_coupon = data_dettaglio_coupon;
	}

	public String getId_evento() {
		return id_evento;
	}

	public void setId_evento(String id_evento) {
		this.id_evento = id_evento;
	}

	public Pronostico getPronostico() {
		return pronostico;
	}

	public void setPronostico(Pronostico pronostico) {
		this.pronostico = pronostico;
	}

	public float getQuota() {
		return quota;
	}

	public void setQuota(float quota) {
		this.quota = quota;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	@Override
	public String toString() {
		return "DettaglioCoupon [id_dettaglio_coupon=" + id_dettaglio_coupon + ", squadra_casa=" + squadra_casa
				+ ", squadra_ospite=" + squadra_ospite + ", data_dettaglio_coupon=" + data_dettaglio_coupon
				+ ", id_evento=" + id_evento + ", pronostico=" + pronostico + ", quota=" + quota + ", esito=" + esito
				+ "]";
	}

}
