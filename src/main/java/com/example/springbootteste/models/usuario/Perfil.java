package com.example.springbootteste.models.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tb_perfil")
public class Perfil implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPerfil;
	private String nome;
	
	@Override
	public String getAuthority() {
		return this.nome;
	}
	
	public Perfil(String nome) {
		this.nome = nome;
	}
	
	public Long getIdPerfil() {
		return idPerfil;
	}
	
	public String getNome() {
		return nome;
	}

}
