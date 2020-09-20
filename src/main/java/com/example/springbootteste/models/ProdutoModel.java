package com.example.springbootteste.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "tb_produto")
public class ProdutoModel extends RepresentationModel<ProdutoModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idProduto;
	private String nome;
	private BigDecimal valor;
	
	public ProdutoModel() {

	}
	
	public ProdutoModel(ProdutoModelDTO produtoDTO) {
		atualizaProduto(produtoDTO);
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
	
	public void atualizaProduto(ProdutoModelDTO produtoDTO) {
		this.nome = produtoDTO.getNome();
		this.valor = produtoDTO.getValor();
	}
	
}
