package com.example.springbootteste.infraestrutura.config.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springbootteste.aplicacao.repositories.usuario.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Dados inválidos"));
	}

}
