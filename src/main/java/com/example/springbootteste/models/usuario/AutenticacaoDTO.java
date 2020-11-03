package com.example.springbootteste.models.usuario;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AutenticacaoDTO {

	private String email;
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken converte() {
		return new UsernamePasswordAuthenticationToken(this.email, this.senha);
	}

}
