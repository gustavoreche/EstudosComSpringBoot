package com.example.springbootteste.config.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootteste.models.handler.ErroDeFormularioDTO;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource mensagem;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
		return simplificaMensagemDeErro(pegaListaDeErro(exception));
	}

	private List<FieldError> pegaListaDeErro(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getFieldErrors();
	}

	private List<ErroDeFormularioDTO> simplificaMensagemDeErro(List<FieldError> listaDeErro) {
		List<ErroDeFormularioDTO> listaDeErroDTO = new ArrayList<ErroDeFormularioDTO>();
		listaDeErro.forEach(cadaErro -> {
			ErroDeFormularioDTO erro = new ErroDeFormularioDTO(cadaErro.getField(), mensagem.getMessage(cadaErro, LocaleContextHolder.getLocale()));
			listaDeErroDTO.add(erro);
		});
		return listaDeErroDTO;
	}

}
