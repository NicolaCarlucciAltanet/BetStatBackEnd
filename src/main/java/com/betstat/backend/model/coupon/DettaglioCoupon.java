package com.betstat.backend.model.coupon;

import java.util.Date;

public class DettaglioCoupon {

	private String id_dettaglio_coupon;

	private Coupon coupon;

	private Squadra squadra_casa;

	private Squadra squadra_ospite;

	private Date data_dettaglio_coupon;

	private String id_evento;

	private Pronostico pronostico;

	private String quota;

	public DettaglioCoupon(String id_dettaglio_coupon, Coupon coupon, Squadra squadra_casa, Squadra squadra_ospite,
			Date data_dettaglio_coupon, String id_evento, Pronostico pronostico, String quota) {
		this.id_dettaglio_coupon = id_dettaglio_coupon;
		this.coupon = coupon;
		this.squadra_casa = squadra_casa;
		this.squadra_ospite = squadra_ospite;
		this.data_dettaglio_coupon = data_dettaglio_coupon;
		this.id_evento = id_evento;
		this.pronostico = pronostico;
		this.quota = quota;
	}

	public String getId_dettaglio_coupon() {
		return id_dettaglio_coupon;
	}

	public void setId_dettaglio_coupon(String id_dettaglio_coupon) {
		this.id_dettaglio_coupon = id_dettaglio_coupon;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
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

	public Date getData_dettaglio_coupon() {
		return data_dettaglio_coupon;
	}

	public void setData_dettaglio_coupon(Date data_dettaglio_coupon) {
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

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

}
