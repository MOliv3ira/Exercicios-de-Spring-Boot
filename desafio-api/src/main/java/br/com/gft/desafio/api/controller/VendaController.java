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

import br.com.gft.desafio.api.model.Venda;
import br.com.gft.desafio.api.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	
	@GetMapping
	public List<Venda> getAll(){
		
		return vendaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Venda> post(@RequestBody Venda venda){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(vendaRepository.save(venda));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venda> getById(@PathVariable Long id){
		
		return vendaRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Venda> put(@PathVariable("id") Long id,
	                                      @RequestBody Venda venda) {
	   return vendaRepository.findById(id)
	           .map(resp -> {
	        	   resp.setTotalCompra(venda.getTotalCompra());
	        	   resp.setDataCompra(venda.getDataCompra());
	        	   resp.setCliente(venda.getCliente());
	        	   resp.setFornecedor(venda.getFornecedor());
	        	   resp.setProdutos(venda.getProdutos());
	        	   Venda updated = vendaRepository.save(resp);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable Long id) {
	   return vendaRepository.findById(id)
	           .map(record -> {
	        	   vendaRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	//GETASC LISTA EM ORDEM ALFABÉTICA CRESCENTE POR NOME
	
	//GETDESC LISTA AS CASAS EM ORDEM ALFABÉTICA DECRESCENTE POR NOME

}
