package br.com.gft.desafio.api.controller;

import java.util.List;

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

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public List<Produto> getAll(){
		return produtoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Produto> post(@RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtoRepository.save(produto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		
		return produtoRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> put(@PathVariable("id") Long id,
	                                      @RequestBody Produto produto) {
	   return produtoRepository.findById(id)
	           .map(resp -> {
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
	
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable Long id) {
	   return produtoRepository.findById(id)
	           .map(record -> {
	        	   produtoRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	//GETASC LISTA EM ORDEM ALFABÉTICA CRESCENTE POR NOME
	
	//GETDESC LISTA AS CASAS EM ORDEM ALFABÉTICA DECRESCENTE POR NOME
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByName(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
}
