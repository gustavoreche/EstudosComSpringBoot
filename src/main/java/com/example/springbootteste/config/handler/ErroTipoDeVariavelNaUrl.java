package com.example.springbootteste.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootteste.models.handler.ErroValorInvalidoDTO;

@RestControllerAdvice
public class ErroTipoDeVariavelNaUrl {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ErroValorInvalidoDTO handle(NumberFormatException exception) {
		return new ErroValorInvalidoDTO("Valor do parâmetro inválido!");
	}

}
