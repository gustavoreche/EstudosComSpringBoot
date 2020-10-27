package com.example.springbootteste.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

public class ProdutoModelDTO extends RepresentationModel<ProdutoModelDTO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idProduto;
	
	@NotNull
	@NotEmpty
	@Size(min = 4, message = "O produto deve ter mais que 3 letras")
	private String nome;
	
	@NotNull
	@Min(value = 1)
	private BigDecimal valor;
	
	public ProdutoModelDTO() {

	}

	public ProdutoModelDTO(ProdutoModel produto) {
		this.idProduto = produto.getIdProduto();
		this.nome = produto.getNome();
		this.valor = produto.getValor();
	}
	
	public long getIdProduto() {
		return idProduto;
	}
	
	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public static List<ProdutoModelDTO> converte(Page<ProdutoModel> listaDeProdutos) {
		List<ProdutoModelDTO> listaDeRetorno = new ArrayList<ProdutoModelDTO>();
		listaDeProdutos.forEach(produto -> {
			listaDeRetorno.add(converte(produto));
		});
		return listaDeRetorno;
	}

	public static Optional<ProdutoModelDTO> converte(Optional<ProdutoModel> produto) {
		return Optional.of(converte(produto.get()));
	}

	public static ProdutoModelDTO converte(ProdutoModel produto) {
		return new ProdutoModelDTO(produto);
	}

	public static List<ProdutoModelDTO> converte(List<ProdutoModel> listaDeProdutos) {
		List<ProdutoModelDTO> listaDeRetorno = new ArrayList<ProdutoModelDTO>();
		listaDeProdutos.forEach(produto -> {
			listaDeRetorno.add(converte(produto));
		});
		return listaDeRetorno;
	}

}
