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

import br.com.gft.desafio.api.model.Fornecedor;
import br.com.gft.desafio.api.repository.FornecedorRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Fornecedores")
@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	
	@ApiOperation("Lista os fornecedores")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Fornecedor> getAll(){
		
		return fornecedorRepository.findAll();
	}
	
	@ApiOperation("Cadastra um novo fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	public ResponseEntity<Fornecedor> post(
			
			@ApiParam(name = "corpo", value = "Representação de um fornecedor")
			@Valid @RequestBody Fornecedor fornecedor){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(fornecedorRepository.save(fornecedor));
	}
	
	@ApiOperation("Busca um fornecedor por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> getById(
			
			@ApiParam(value = "ID de um fornecedor", example = "1")
			@PathVariable Long id){
		
		return fornecedorRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Atualiza um fornecedor por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> put(
			
			@ApiParam(value = "ID de um fornecedor", example = "1")
			@PathVariable("id") Long id,@Valid @RequestBody Fornecedor fornecedor) {
		return fornecedorRepository.findById(id).map(resp -> {
			resp.setNome(fornecedor.getNome());
			resp.setCnpj(fornecedor.getCnpj());
			Fornecedor updated = fornecedorRepository.save(resp);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Exclui um fornecedor por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(
			
			@ApiParam(value = "ID de um fornecedor", example = "1")
			@PathVariable Long id) {
	   return fornecedorRepository.findById(id)
	           .map(record -> {
	        	   fornecedorRepository.deleteById(id);
	               return ResponseEntity.noContent().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Lista os fornecedores em ordem alfabética crescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	public List<Fornecedor> getAsc(){
		return fornecedorRepository.findAllByOrderByNomeAsc();
	}
	
	@ApiOperation("Lista os fornecedores em ordem alfabética decrescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	public List<Fornecedor> getDesc(){
		return fornecedorRepository.findAllByOrderByNomeDesc();
	}
	
	@ApiOperation("Busca fornecedor por Nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Fornecedor>> getByName(
			
			@ApiParam(value = "Nome de um fornecedor", example = "Dell")
			@PathVariable String nome){
		return ResponseEntity.ok(fornecedorRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	

}
