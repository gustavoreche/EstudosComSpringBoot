package com.example.springbootteste.infraestrutura.controllers.autenticacao;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootteste.infraestrutura.config.security.services.TokenService;
import com.example.springbootteste.models.usuario.AutenticacaoDTO;
import com.example.springbootteste.models.usuario.TokenDTO;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	/*
	 * INSERT INTO tb_usuario (id_usuario,email,nome,senha) VALUES
	 * (1,'gustavo@teste.com.br','Gustavo','$2a$10$09gPRG9pZXeHAnSpRk9NPuofBTkJqzibfdL4DmNEmRklq7468TS7.');
	 */
	
	@Autowired
	AuthenticationManager authenticationManager; 
	
	@Autowired
	TokenService tokenService; 
	
	@PostMapping()
	public ResponseEntity<?> autentica(@RequestBody @Valid AutenticacaoDTO usuarioESenha){
		UsernamePasswordAuthenticationToken dadosDoLogin = usuarioESenha.converte();
		try {
			Authentication autenticacao = authenticationManager.authenticate(dadosDoLogin);		
			String token = tokenService.gerarToken(autenticacao);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
