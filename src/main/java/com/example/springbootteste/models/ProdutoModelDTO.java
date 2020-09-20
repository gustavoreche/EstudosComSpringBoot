package com.example.springbootteste.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.RepresentationModel;

public class ProdutoModelDTO extends RepresentationModel<ProdutoModelDTO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private long idProduto;
	private String nome;
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

	public static List<ProdutoModelDTO> converte(List<ProdutoModel> listaDeProdutos) {
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

	public ProdutoModel converteParaOModel() {
		return new ProdutoModel(this);
	}

	public void atualizaProduto(ProdutoModel produtoModel) {
		produtoModel.atualizaProduto(this);
				
	}

}
