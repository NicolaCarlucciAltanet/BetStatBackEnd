package com.betstat.backend.model.coupon;

public class Utente {

	private String id_utente;

	private String nome_utente;

	private String cognome_utente;

	private String cf_utente;

	private String email_utente;

	private String token_utente;

	private String token_validate_utente;

	private String last_access_utente;

	private String password_utente;

	private String indirizzo_utente;

	private String telefono_utente;

	public Utente(String id_utente, String nome_utente, String cognome_utente, String cf_utente, String email_utente,
			String token_utente, String token_validate_utente, String last_access_utente, String password_utente,
			String indirizzo_utente, String telefono_utente) {
		this.id_utente = id_utente;
		this.nome_utente = nome_utente;
		this.cognome_utente = cognome_utente;
		this.cf_utente = cf_utente;
		this.email_utente = email_utente;
		this.token_utente = token_utente;
		this.token_validate_utente = token_validate_utente;
		this.last_access_utente = last_access_utente;
		this.password_utente = password_utente;
		this.indirizzo_utente = indirizzo_utente;
		this.telefono_utente = telefono_utente;
	}

	public String getId_utente() {
		return id_utente;
	}

	public void setId_utente(String id_utente) {
		this.id_utente = id_utente;
	}

	public String getNome_utente() {
		return nome_utente;
	}

	public void setNome_utente(String nome_utente) {
		this.nome_utente = nome_utente;
	}

	public String getCognome_utente() {
		return cognome_utente;
	}

	public void setCognome_utente(String cognome_utente) {
		this.cognome_utente = cognome_utente;
	}

	public String getCf_utente() {
		return cf_utente;
	}

	public void setCf_utente(String cf_utente) {
		this.cf_utente = cf_utente;
	}

	public String getEmail_utente() {
		return email_utente;
	}

	public void setEmail_utente(String email_utente) {
		this.email_utente = email_utente;
	}

	public String getToken_utente() {
		return token_utente;
	}

	public void setToken_utente(String token_utente) {
		this.token_utente = token_utente;
	}

	public String getToken_validate_utente() {
		return token_validate_utente;
	}

	public void setToken_validate_utente(String token_validate_utente) {
		this.token_validate_utente = token_validate_utente;
	}

	public String getLast_access_utente() {
		return last_access_utente;
	}

	public void setLast_access_utente(String last_access_utente) {
		this.last_access_utente = last_access_utente;
	}

	public String getPassword_utente() {
		return password_utente;
	}

	public void setPassword_utente(String password_utente) {
		this.password_utente = password_utente;
	}

	public String getIndirizzo_utente() {
		return indirizzo_utente;
	}

	public void setIndirizzo_utente(String indirizzo_utente) {
		this.indirizzo_utente = indirizzo_utente;
	}

	public String getTelefono_utente() {
		return telefono_utente;
	}

	public void setTelefono_utente(String telefono_utente) {
		this.telefono_utente = telefono_utente;
	}

}
