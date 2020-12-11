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

import br.com.gft.desafio.api.model.Venda;
import br.com.gft.desafio.api.repository.VendaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Vendas")
@RestController
@RequestMapping("/vendas")
public class VendaController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	
	@ApiOperation("Lista as vendas")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Venda> getAll(){
		
		return vendaRepository.findAll();
	}
	
	@ApiOperation("Cadastra um nova venda")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping
	public ResponseEntity<Venda> post(
			
			@ApiParam(name = "corpo", value = "Representação de uma venda")
			@Valid @RequestBody Venda venda){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(vendaRepository.save(venda));
	}
	
	@ApiOperation("Busca venda por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<Venda> getById(
			
			@ApiParam(value = "ID de uma venda", example = "1")
			@PathVariable Long id){
		
		return vendaRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation("Atualiza venda por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Venda> put(
			
			@ApiParam(value = "ID de uma venda", example = "1")
			@PathVariable("id") Long id, @Valid @RequestBody Venda venda) {
		return vendaRepository.findById(id).map(resp -> {
			resp.setTotalCompra(venda.getTotalCompra());
			resp.setDataCompra(venda.getDataCompra());
			resp.setCliente(venda.getCliente());
			resp.setFornecedor(venda.getFornecedor());
			resp.setProdutos(venda.getProdutos());
			Venda updated = vendaRepository.save(resp);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	
	@ApiOperation("Exclui venda por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(
			
			@ApiParam(value = "ID de uma venda", example = "1")
			@PathVariable Long id) {
	   return vendaRepository.findById(id)
	           .map(record -> {
	        	   vendaRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
		

}
