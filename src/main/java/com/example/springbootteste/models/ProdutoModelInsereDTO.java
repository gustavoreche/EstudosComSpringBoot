package com.example.springbootteste.models;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProdutoModelInsereDTO {
	
	@NotNull
	@NotEmpty
	@Size(min = 4, message = "O produto deve ter mais que 3 letras")
	private String nome;
	
	@NotNull
	@Min(value = 1)
	private BigDecimal valor;
	
	public ProdutoModelInsereDTO() {

	}
	
	public ProdutoModel converteParaOModel() {
		return new ProdutoModel(this.nome, this.valor);
	}
	
	public void atualizaProduto(ProdutoModel produtoModel) {
		produtoModel.atualizaProduto(this);
	}
	
	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

}
