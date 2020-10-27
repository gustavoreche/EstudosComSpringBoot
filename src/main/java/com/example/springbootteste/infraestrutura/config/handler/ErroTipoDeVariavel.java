package com.example.springbootteste.infraestrutura.config.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootteste.models.handler.ErroDeFormularioDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class ErroTipoDeVariavel {
	
	private static final String REGEX_PARA_PEGAR_NOME_DO_CAMPO = "\\[\\\".+\\\"\\]";
	
	@Autowired
	MessageSource internacionalizacaoDeMensagem;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	public ErroDeFormularioDTO handle(InvalidFormatException exception) {
		return new ErroDeFormularioDTO(pegaNomeDoCampo(exception), "Valor do parâmetro inválido!");
	}

	private String pegaNomeDoCampo(InvalidFormatException exception) {
		Pattern modeloParaPegarNomeDoCampo = Pattern.compile(REGEX_PARA_PEGAR_NOME_DO_CAMPO);
		Matcher nomeDoCampo = modeloParaPegarNomeDoCampo.matcher(exception.getMessage());
		while(nomeDoCampo.find()) {
			String campo = nomeDoCampo.group(0).replace("[\"", "");
			return campo.replace("\"]", "");
			
		}
		return internacionalizacaoDeMensagem.getMessage("nao.encontrou.campo", null, LocaleContextHolder.getLocale());
		
	}

}
