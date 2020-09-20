package com.example.springbootteste.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class ProdutoModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idProduto;
	private String nome;
	private BigDecimal valor;
	private LocalDate dataCriacao = LocalDate.now();

	public ProdutoModel() {

	}

	protected ProdutoModel(ProdutoModelDTO produtoDTO) {
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

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	protected void atualizaProduto(ProdutoModelDTO produtoDTO) {
		this.nome = produtoDTO.getNome();
		this.valor = produtoDTO.getValor();
	}

}
