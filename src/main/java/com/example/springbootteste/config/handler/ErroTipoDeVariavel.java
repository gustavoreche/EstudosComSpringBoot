package com.example.springbootteste.config.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootteste.models.handler.ErroDeFormularioDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class ErroTipoDeVariavel {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	public ErroDeFormularioDTO handle(InvalidFormatException exception) {
		return new ErroDeFormularioDTO(pegaNomeDoCampo(exception), "Valor do parâmetro inválido!");
	}

	private String pegaNomeDoCampo(InvalidFormatException exception) {
		Pattern modeloParaSepararCaractere = Pattern.compile("\\[\\\".+\\\"\\]");
		Matcher caractereSeparado = modeloParaSepararCaractere.matcher(exception.getMessage());
		while(caractereSeparado.find()) {
			String campo = caractereSeparado.group(0).replace("[\"", "");
			return campo.replace("\"]", "");
			
		}
		return "Não foi possível localizar o campo.";
		
	}

}
