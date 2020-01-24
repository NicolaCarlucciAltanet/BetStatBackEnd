package com.betstat.backend.utilities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.betstat.backend.business.model.coupon.Coupon;
import com.betstat.backend.business.model.coupon.DettaglioCoupon;
import com.betstat.backend.business.model.coupon.Esito;
import com.betstat.backend.business.model.coupon.Pronostico;
import com.betstat.backend.business.model.coupon.Squadra;
import com.betstat.backend.business.model.coupon.Tipo;
import com.betstat.backend.business.model.coupon.Utente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtilities {

	/**
	 * Converte una stringa in un oggetto di tipo Coupon
	 * 
	 * @param couponString
	 * @return Coupon
	 */
	public static Coupon getCouponFromString(String couponString) {
		Gson gson = new Gson();
		return gson.fromJson(couponString, Coupon.class);
	}

	/**
	 * Converte un oggetto di tipo Coupon in json
	 * 
	 * @param coupon
	 * @return json del Coupon
	 */
	public static String getStringFromCoupon(Coupon coupon) {
		Gson gson = new Gson();
		return gson.toJson(coupon);
	}

	/**
	 * Converte una stringa in un oggetto di tipo Tipo
	 * 
	 * @param tipoString
	 * @return Tipo
	 */
	public static Tipo getTipoFromString(String tipoString) {
		Gson gson = new Gson();
		return gson.fromJson(tipoString, Tipo.class);
	}

	/**
	 * Converte un oggetto di tipo Tipo in json
	 * 
	 * @param Tipo
	 * @return json del Tipo
	 */
	public static String getStringFromTipo(Tipo tipo) {
		Gson gson = new Gson();
		return gson.toJson(tipo);
	}

	/**
	 * Converte una stringa in un oggetto di tipo Esito
	 * 
	 * @param tipoString
	 * @return Tipo
	 */
	public static Esito getEsitoFromString(String esitoString) {
		Gson gson = new Gson();
		return gson.fromJson(esitoString, Esito.class);
	}

	/**
	 * Converte un oggetto di tipo Esito in json
	 * 
	 * @param esito
	 * @return json del Esito
	 */
	public static String getStringFromTipo(Esito esito) {
		Gson gson = new Gson();
		return gson.toJson(esito);
	}

	/**
	 * Converte una stringa in un oggetto di tipo Utente
	 * 
	 * @param utenteString
	 * @return Utente
	 */
	public static Utente getUtenteFromString(String utenteString) {
		Gson gson = new Gson();
		return gson.fromJson(utenteString, Utente.class);
	}

	/**
	 * Converte un oggetto di tipo Utente in json
	 * 
	 * @param utente
	 * @return json del Utente
	 */
	public static String getStringFromUtente(Utente utente) {
		Gson gson = new Gson();
		return gson.toJson(utente);
	}

	/**
	 * Converte una stringa in un oggetto di tipo Squadra
	 * 
	 * @param squadraString
	 * @return Squadra
	 */
	public static Squadra getSquadraFromString(String squadraString) {
		Gson gson = new Gson();
		return gson.fromJson(squadraString, Squadra.class);
	}

	/**
	 * Converte un oggetto di tipo Squadra in json
	 * 
	 * @param squadra
	 * @return json di Squadra
	 */
	public static String getStringFromSquadra(Squadra squadra) {
		Gson gson = new Gson();
		return gson.toJson(squadra);
	}

	/**
	 * Converte una stringa in un oggetto di tipo Pronostico
	 * 
	 * @param pronosticoString
	 * @return Pronostico
	 */
	public static Pronostico getPronosticoFromString(String pronosticoString) {
		Gson gson = new Gson();
		return gson.fromJson(pronosticoString, Pronostico.class);
	}

	/**
	 * Converte un oggetto di tipo Pronostico in json
	 * 
	 * @param pronostico
	 * @return json di Pronostico
	 */
	public static String getStringFromPronostico(Pronostico pronostico) {
		Gson gson = new Gson();
		return gson.toJson(pronostico);
	}

	/**
	 * Converte una stringa in una lista di DettaglioCoupon
	 * 
	 * @param listDettaglioCouponString
	 * @return List DettaglioCoupon
	 */
	public static List<DettaglioCoupon> getListDettaglioCouponFromString(String listDettaglioCouponString) {
		Gson gson = new Gson();
		Type listOfMyClassObject = new TypeToken<ArrayList<DettaglioCoupon>>() {
		}.getType();
		return gson.fromJson(listDettaglioCouponString, listOfMyClassObject);
	}

	/**
	 * Converte una lista di dettaglioCoupon in json
	 * 
	 * @param listDettaglioCoupon
	 * @return json di listDettaglioCoupon
	 */
	public static String getStringFromListDettaglioCoupon(List<DettaglioCoupon> listDettaglioCoupon) {
		Gson gson = new Gson();
		return gson.toJson(listDettaglioCoupon);
	}

}
