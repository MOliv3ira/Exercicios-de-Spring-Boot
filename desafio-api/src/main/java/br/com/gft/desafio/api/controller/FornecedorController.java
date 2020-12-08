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

import br.com.gft.desafio.api.model.Fornecedor;
import br.com.gft.desafio.api.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@GetMapping
	public List<Fornecedor> getAll(){
		
		return fornecedorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> post(@RequestBody Fornecedor fornecedor){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(fornecedorRepository.save(fornecedor));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> getById(@PathVariable Long id){
		
		return fornecedorRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> put(@PathVariable("id") Long id,
	                                      @RequestBody Fornecedor fornecedor) {
	   return fornecedorRepository.findById(id)
	           .map(resp -> {
	        	   resp.setNome(fornecedor.getNome());
	        	   resp.setCnpj(fornecedor.getCnpj());
	        	   Fornecedor updated = fornecedorRepository.save(resp);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable Long id) {
	   return fornecedorRepository.findById(id)
	           .map(record -> {
	        	   fornecedorRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	//GETASC LISTA EM ORDEM ALFABÉTICA CRESCENTE POR NOME
	
	//GETDESC LISTA AS CASAS EM ORDEM ALFABÉTICA DECRESCENTE POR NOME
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Fornecedor>> getByName(@PathVariable String nome){
		return ResponseEntity.ok(fornecedorRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	

}
