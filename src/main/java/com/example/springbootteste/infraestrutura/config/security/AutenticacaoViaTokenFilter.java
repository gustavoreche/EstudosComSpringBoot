package com.example.springbootteste.infraestrutura.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springbootteste.aplicacao.repositories.usuario.UsuarioRepository;
import com.example.springbootteste.infraestrutura.config.security.services.TokenService;
import com.example.springbootteste.models.usuario.UsuarioModel;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperaToken(request);
		if(tokenService.ehValido(token))
			autenticaCliente(token);
		filterChain.doFilter(request, response);
	}
	
	private String recuperaToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(naoTemToken(token))
			return "";
		return token.substring(7, token.length());
	}
	
	private boolean naoTemToken(String token) {
		return token == null || token.isEmpty() || !token.startsWith("Bearer ");
	}

	private void autenticaCliente(String token) {
		Long idUsuario = tokenService.pegaIdDoUsuario(token);
		UsuarioModel usuario = usuarioRepository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(autenticacao);
	}

}
