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

import br.com.gft.desafio.api.model.Cliente;
import br.com.gft.desafio.api.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@ApiOperation("Lista os clientes")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Cliente> getAll(){
		
		return clienteRepository.findAll();
	}
	
	@ApiOperation("Cadastra um novo cliente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	public ResponseEntity<Cliente> post(
			
			@ApiParam(name = "corpo", value = "Representação de um cliente")
			@Valid @RequestBody Cliente cliente){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(clienteRepository.save(cliente));
	}
	
	@ApiOperation("Busca um cliente por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getById(
			
			@ApiParam(value = "ID de um cliente", example = "1")
			@PathVariable Long id){
		
		return clienteRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Atualiza um cliente por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> put(
			
			@ApiParam(value = "ID de um cliente", example = "1")
			@PathVariable("id") Long id,@Valid @RequestBody Cliente cliente) {
		return clienteRepository.findById(id).map(resp -> {
			resp.setNome(cliente.getNome());
			resp.setEmail(cliente.getEmail());
			resp.setSenha(cliente.getSenha());
			resp.setDocumento(cliente.getDocumento());
			Cliente updated = clienteRepository.save(resp);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Exclui um cliente por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(
			
			@ApiParam(value = "ID de um cliente", example = "1")
			@PathVariable Long id) {
	   return clienteRepository.findById(id)
	           .map(record -> {
	        	   clienteRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Lista os clientes em ordem alfabética crescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	public List<Cliente> getAsc(){
		return clienteRepository.findAllByOrderByNomeAsc();
	}
	
	@ApiOperation("Lista os clientes em ordem alfabética decrescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	public List<Cliente> getDesc(){
		return clienteRepository.findAllByOrderByNomeDesc();
	}
	
	@ApiOperation("Busca cliente por Nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Cliente>> getByName(
			
			@ApiParam(value = "Nome de um cliente", example = "Maria")
			@PathVariable String nome){
		return ResponseEntity.ok(clienteRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	
}
