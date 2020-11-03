package com.example.springbootteste.aplicacao.repositories.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootteste.models.usuario.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

	Optional<UsuarioModel> findByEmail(String email);
}
