package com.example.springbootteste.infraestrutura.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootteste.aplicacao.repositories.ProdutoRepository;
import com.example.springbootteste.models.produto.ProdutoModel;
import com.example.springbootteste.models.produto.ProdutoModelDTO;
import com.example.springbootteste.models.produto.ProdutoModelInsereDTO;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	private static final String LISTA_PRODUTOS = "Lista de Produtos";
	
	@GetMapping()
	public ResponseEntity<List<ProdutoModelDTO>> pegaTodosProdutos(@RequestParam(required = false) Integer pagina, 
			@RequestParam(required = false) Integer quantidade){
		List<ProdutoModelDTO> listaDeProdutosDTO = new ArrayList<ProdutoModelDTO>();
		listaDeProdutosDTO = pegaListaDeProdutos(pagina, quantidade);
		if(listaDeProdutosDTO != null && listaDeProdutosDTO.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		criaLinks(listaDeProdutosDTO);
		return new ResponseEntity<List<ProdutoModelDTO>>(listaDeProdutosDTO, HttpStatus.OK);
	}

	private List<ProdutoModelDTO> pegaListaDeProdutos(Integer pagina, Integer quantidade) {
		if(temPaginacao(pagina, quantidade)) {
			return listaDeProdutosComPaginacao(pagina, quantidade);			
		} 
		return listaDeProdutos();
	}
	
	private boolean temPaginacao(Integer pagina, Integer quantidade) {
		return (pagina != null && pagina >= 0) && (quantidade != null && quantidade > 0);
	}
	
	private List<ProdutoModelDTO> listaDeProdutosComPaginacao(int pagina, int quantidade) {
		Pageable paginacao = PageRequest.of(pagina, quantidade);
		return ProdutoModelDTO.converte(produtoRepository.findAll(paginacao));
	}

	private List<ProdutoModelDTO> listaDeProdutos() {
		return ProdutoModelDTO.converte(produtoRepository.findAll());
	}

	private void criaLinks(List<ProdutoModelDTO> listaDeProdutosDTO) {
		for (ProdutoModelDTO produtoDTO : listaDeProdutosDTO) {
			adicionaLink(produtoDTO);
		}
	}
	
	private void adicionaLink(ProdutoModelDTO produtoDTO) {
		adicionaLink(produtoDTO, false);
	}

	private void adicionaLink(ProdutoModelDTO produtoDTO, boolean linkDeTodosOsProdutos) {
		if(linkDeTodosOsProdutos) {
			produtoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
					.methodOn(ProdutoController.class).pegaTodosProdutos(0, 0))
					.withRel(LISTA_PRODUTOS));			
		} else {
			produtoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
					.methodOn(ProdutoController.class).pegaUmProduto(produtoDTO.getIdProduto()))
					.withSelfRel());
		}
	}
	
	@GetMapping("/{id}")
	@Cacheable(value = "listaPorId")
	public ResponseEntity<ProdutoModelDTO> pegaUmProduto(@PathVariable(value = "id") long id){
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if(!produto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<ProdutoModelDTO> produtoDTO = ProdutoModelDTO.converte(produto);
		adicionaLink(produtoDTO.get(), true);
		return new ResponseEntity<ProdutoModelDTO>(produtoDTO.get(), HttpStatus.OK);
	}
	
	@PostMapping()
	@CacheEvict(value = "listaPorId", allEntries = true)
	public ResponseEntity<ProdutoModelDTO> salvaProduto(@RequestBody @Valid ProdutoModelInsereDTO produto){
		ProdutoModelDTO produtoDTO = ProdutoModelDTO.converte(produtoRepository.save(produto.converteParaOModel()));
		adicionaLink(produtoDTO);
		return new ResponseEntity<ProdutoModelDTO>(produtoDTO, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listaPorId", allEntries = true)
	public ResponseEntity<?> deletaProduto(@PathVariable(value = "id") long id){
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if(!produto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produtoRepository.delete(produto.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaPorId", allEntries = true)
	public ResponseEntity<ProdutoModelDTO> atualizaProduto(@PathVariable(value = "id") long id,
			@RequestBody @Valid ProdutoModelInsereDTO produtoDTO){
		Optional<ProdutoModel> produtoExistente = produtoRepository.findById(id);
		if(!produtoExistente.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produtoDTO.atualizaProduto(produtoExistente.get());
		return new ResponseEntity<ProdutoModelDTO>(ProdutoModelDTO.converte(produtoExistente.get()), HttpStatus.OK);
	}
	
}
