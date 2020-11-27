package br.com.gft.estudo.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.estudo.api.event.RecursoCriadoEvent;
import br.com.gft.estudo.api.model.Categoria;
import br.com.gft.estudo.api.repository.CategoriaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Categorias") //VINCULANDO A TAG DA CLASSE SPRINGFOXCONFIG COM O CONTROLLER NA DOCUMENTAÇÃO.
@RestController //RETORNA UM JSON
@RequestMapping("/categorias") // MAPIAMENTO DA REQUISIÇÃO URL
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation("Listar categorias")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping
	public List<Categoria> listar(){
		
		return categoriaRepository.findAll();
	}
	
	@ApiOperation("Criar uma nova categoria")
//	@ResponseStatus(HttpStatus.CREATED) //RETORNA '201 CREATED' TODA VEZ QUE ADD UMA CATEGORIA NOVA.
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PostMapping 
	public ResponseEntity<Categoria> criar(
			
			@ApiParam(name = "corpo" ,value = "Representação de uma categoria")
			@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@ApiOperation("Buscar categoria por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> getById(
			
			@ApiParam(value = "ID de uma categoria", example = "1")
			@PathVariable Long codigo){
		return categoriaRepository.findById(codigo).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
}
