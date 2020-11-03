package com.example.springbootteste.infraestrutura.config.security.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.springbootteste.models.usuario.UsuarioModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${jwt.data.expiracao}")
	private String tempoDeExpiracao;
	
	@Value("${jwt.senha}")
	private String senha;

	public String gerarToken(Authentication autenticacao) {
		UsuarioModel usuarioLogado = (UsuarioModel) autenticacao.getPrincipal();
		Date dataAtual = new Date();
		Date dataDeExpiracao = new Date(dataAtual.getTime() + Long.parseLong(tempoDeExpiracao));
		
		return Jwts.builder()
				.setIssuer("API de Produto")
				.setSubject(usuarioLogado.getIdUsuario().toString())
				.setIssuedAt(dataAtual)
				.setExpiration(dataDeExpiracao)
				.signWith(SignatureAlgorithm.HS256, senha)
				.compact();
	}
	
	

}
