package com.example.springbootteste.infraestrutura.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootteste.models.handler.ErroValorInvalidoDTO;

@RestControllerAdvice
public class ErroTipoDeVariavelNaUrl {
	
	@Autowired
	MessageSource internacionalizacaoDeMensagem;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ErroValorInvalidoDTO handle(NumberFormatException exception) {
		return new ErroValorInvalidoDTO(internacionalizacaoDeMensagem.getMessage("valor.parametro.invalido", null, LocaleContextHolder.getLocale()));
	}

}
