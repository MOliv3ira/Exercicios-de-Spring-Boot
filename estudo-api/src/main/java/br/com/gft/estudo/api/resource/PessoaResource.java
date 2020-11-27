package br.com.gft.estudo.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.estudo.api.event.RecursoCriadoEvent;
import br.com.gft.estudo.api.model.Pessoa;
import br.com.gft.estudo.api.repository.PessoaRepository;
import br.com.gft.estudo.api.service.PessoaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation("Cadastra uma nova pessoa")
	@PostMapping 
	public ResponseEntity<Pessoa> criar(
			
			@ApiParam(name = "corpo", value = "Representação de uma pessoa")
			@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@ApiOperation("Busca uma pessoa por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> getById(
			
			@ApiParam(value = "ID de uma pessoa", example = "1")
			@PathVariable Long codigo){
		
		return pessoaRepository.findById(codigo).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	} 
	
	@ApiOperation("Remove uma pessoa por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
			
			@ApiParam(value = "ID de uma pessoa", example = "1")
			@PathVariable Long codigo) {
		
		pessoaRepository.deleteById(codigo);
	}
	
	@ApiOperation("Atualiza pessoa por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(
			
			@ApiParam(value = "ID de uma pessoa", example = "1") 
			@PathVariable Long codigo, 
			@ApiParam(name = "corpo", value = "Representação de uma pessoa com novos dados")
			@Valid @RequestBody Pessoa pessoa){

		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	
	}
	
	@ApiOperation("Atualiza status por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(
			
			@ApiParam(value = "ID de uma pessoa", example = "1")
			@PathVariable Long codigo,
			@ApiParam(name = "corpo" ,value = "Representação de uma pessoa com novo status")
			@RequestBody Boolean ativo) {
		
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	

}
