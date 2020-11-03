package com.example.springbootteste.models.usuario;

public class TokenDTO {

	private String token;
	private String tipoDeToken;

	public TokenDTO(String token, String tipoDeToken) {
		this.token = token;
		this.tipoDeToken = tipoDeToken;
	}

	public String getToken() {
		return token;
	}

	public String getTipoDeToken() {
		return tipoDeToken;
	}

}
