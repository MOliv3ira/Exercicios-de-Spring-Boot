package com.minharedesocial.redesocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minharedesocial.redesocial.model.Postagem;
import com.minharedesocial.redesocial.repository.PostagemRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") //(allowedHeaders)Para puxar as outras tabelas que estão ligadas na tabela postagem
@RequestMapping("/postagem")
public class PostagemController {
	
	@Autowired
	public PostagemRepository repository;
	
	@GetMapping 	//Estou me referindo a um metodo Get para vir uma lista de postagem
	public ResponseEntity<List<Postagem>> GetAll(){
		
		return ResponseEntity.ok(repository.findAll()); 					//Chamando o Metodo FindAll para trazer(retornar) uma LISTA de Dados
	}
	
	
	@GetMapping("/{id}") 													//Defini que irei receber como um parametro atravez da URL //localhost:8080/postagem/2  <-- o Numero 2 é o parametro
	public ResponseEntity<Postagem> GetById(@PathVariable long id){  			// @PathVariable infoma que o valor virá da URL como paramentro
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) 
				.orElse(ResponseEntity.notFound().build()); 				// Metodo Map retorma uma valor do tipo postagem e caso exista ele retorna um objeto do tipo 200 OK e caso não exista ele retorna um badResquest 404
	}
	
	 /*@GetMapping("/descricao/{descricao}")
	    public ResponseEntity<List<Postagem>> getByDescription(@PathVariable String descricao){
	        return ResponseEntity.ok(repository.findAllByDescricaoContainsIgnoreCase(descricao));*/
	
	
	
	
	
	@PostMapping 															//Metodo Post, iremos postar (inserir) as informações em nosso banco de dados
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem){  			//@RequestBody -> Informa que o objeto postagem virá via Body (Corpo da requisição)
		
		return ResponseEntity.status(HttpStatus.CREATED)  					//ResponseEntity.status(HttpStatus.CREATED) retorna o status 201 Create para o cliente
				.body(repository.save(postagem));    							//.body(repository.save(turma)) retorna na body -- Corpo o objeto Criado
	}																		//Lembrando que quando o objeto vem sem o ID estamos fazendo uma atualização.
	
	
	
	
	
	@PutMapping																//Metodo que fará a Atualização nos dados que ja existem na nossa base
	public ResponseEntity<Postagem> Put(@RequestBody Postagem postagem){				//@RequestBody -> Informa que o objeto postagem virá via Body (Corpo da requisição)
		return ResponseEntity.status(HttpStatus.OK) 						//ResponseEntity.status(HttpStatus.OK) retorna o status 200 OK para o cliente
				.body(repository.save(postagem)); 								//.body(repository.save(turma)) retorna na body -- Corpo o objeto Atualizado
	}																		//Lembrando que quando o objeto vem com o ID estamos fazendo uma atualização.
	
	
	
	
	
	@DeleteMapping("/{id}")													//Metodo que irá deletar os dados do nosso banco
	public void Delete (@PathVariable long id) {							// @PathVariable infoma que o valor virá da URL como paramentro
		repository.deleteById(id);											//O metodo deverá ser void, ou seja não retorna nada
	}
	
	
	

}
