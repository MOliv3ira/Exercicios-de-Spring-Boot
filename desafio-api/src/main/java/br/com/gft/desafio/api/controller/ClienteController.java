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

import br.com.gft.desafio.api.model.Cliente;
import br.com.gft.desafio.api.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@GetMapping
	public List<Cliente> getAll(){
		
		return clienteRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Cliente> post(@RequestBody Cliente cliente){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(clienteRepository.save(cliente));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getById(@PathVariable Long id){
		
		return clienteRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> put(@PathVariable("id") Long id,
	                                      @RequestBody Cliente cliente) {
	   return clienteRepository.findById(id)
	           .map(resp -> {
	        	   resp.setNome(cliente.getNome());
	        	   resp.setEmail(cliente.getEmail());
	        	   resp.setSenha(cliente.getSenha());
	        	   resp.setDocumento(cliente.getDocumento());
	               Cliente updated = clienteRepository.save(resp);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable Long id) {
	   return clienteRepository.findById(id)
	           .map(record -> {
	        	   clienteRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	//GETASC LISTA EM ORDEM ALFABÉTICA CRESCENTE POR NOME
	
	//GETDESC LISTA AS CASAS EM ORDEM ALFABÉTICA DECRESCENTE POR NOME
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Cliente>> getByName(@PathVariable String nome){
		return ResponseEntity.ok(clienteRepository.findAllByNomeContainingIgnoreCase(nome));
	}
}
