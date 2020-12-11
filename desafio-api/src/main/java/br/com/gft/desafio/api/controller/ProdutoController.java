package br.com.gft.desafio.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.desafio.api.model.Produto;
import br.com.gft.desafio.api.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@ApiOperation("Lista os produtos")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Produto> getAll(){
		return produtoRepository.findAll();
	}
	
	@ApiOperation("Cadastra um novo produto")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	public ResponseEntity<Produto> post(
			
			@ApiParam(name = "corpo", value = "Representação de um produto")
			@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtoRepository.save(produto));
	}
	
	@ApiOperation("Busca produto por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(
			
			@ApiParam(value = "ID de um produto", example = "1")
			@PathVariable Long id){
		
		return produtoRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Atualiza produto por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Produto> put(
			
			@ApiParam(value = "ID de um produto", example = "1")
			@PathVariable("id") Long id,@Valid @RequestBody Produto produto) {
		return produtoRepository.findById(id).map(resp -> {
			resp.setNome(produto.getNome());
			resp.setCodigoProduto(produto.getCodigoProduto());
			resp.setValor(produto.getValor());
			resp.setPromocao(produto.isPromocao());
			resp.setValorPromo(produto.getValorPromo());
			resp.setCategoria(produto.getCategoria());
			resp.setImagem(produto.getImagem());
			resp.setQuantidade(produto.getQuantidade());

			Produto updated = produtoRepository.save(resp);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	
	@ApiOperation("Exclui produto por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(
			
			@ApiParam(value = "ID de um produto", example = "1")
			@PathVariable Long id) {
	   return produtoRepository.findById(id)
	           .map(record -> {
	        	   produtoRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	
	@ApiOperation("Lista os produtos em ordem alfabética crescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	public List<Produto> getAsc(){
		return produtoRepository.findAllByOrderByNomeAsc();
	}
	
	
	@ApiOperation("Lista os produtos em ordem alfabética decrescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	public List<Produto> getDesc(){
		return produtoRepository.findAllByOrderByNomeDesc();
	}
	
	@ApiOperation("Busca produto por Nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByName(
			
			@ApiParam(value = "Nome de um produto", example = "Celery")
			@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
}
