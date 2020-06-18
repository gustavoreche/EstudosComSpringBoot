package com.example.springbootteste.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootteste.models.ProdutoModel;
import com.example.springbootteste.repositories.ProdutoRepository;

@RestController
public class ProdutoController {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	private static final String LISTA_PRODUTOS = "Lista de Produtos";
	
	@GetMapping("/produtos")
	public ResponseEntity<List<ProdutoModel>> pegaTodosProdutos(){
		List<ProdutoModel> listaDeProdutos = produtoRepository.findAll();
		if(listaDeProdutos != null && listaDeProdutos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		for (ProdutoModel produto : listaDeProdutos) {
			long id = produto.getIdProduto();
			produto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
					.methodOn(ProdutoController.class).pegaUmProduto(id))
		            .withSelfRel());
		}
		return new ResponseEntity<List<ProdutoModel>>(listaDeProdutos, HttpStatus.OK);
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<ProdutoModel> pegaUmProduto(@PathVariable(value = "id") long id){
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if(!produto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produto.get()
				.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(ProdutoController.class).pegaTodosProdutos())
	            .withRel(LISTA_PRODUTOS));
		return new ResponseEntity<ProdutoModel>(produto.get(), HttpStatus.OK);
	}
	
	@PostMapping("/produtos")
	public ResponseEntity<ProdutoModel> salvaProduto(@RequestBody @Valid ProdutoModel produto){
		return new ResponseEntity<ProdutoModel>(produtoRepository.save(produto), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<?> deletaProduto(@PathVariable(value = "id") long id){
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if(!produto.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produtoRepository.delete(produto.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/produtos/{id}")
	public ResponseEntity<ProdutoModel> atualizaProduto(@PathVariable(value = "id") long id,
			@RequestBody @Valid ProdutoModel produto){
		Optional<ProdutoModel> produtoExistente = produtoRepository.findById(id);
		if(!produtoExistente.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		produto.setIdProduto(produtoExistente.get().getIdProduto());
		return new ResponseEntity<ProdutoModel>(produtoRepository.save(produto), HttpStatus.OK);
	}
	
}
