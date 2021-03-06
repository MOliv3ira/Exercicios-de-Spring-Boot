package com.minharedesocial.redesocial.controller;

import java.util.List;
import java.util.Optional;

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

import com.minharedesocial.redesocial.model.UserLogin;
import com.minharedesocial.redesocial.model.Usuario;
import com.minharedesocial.redesocial.repository.UsuarioRepository;
import com.minharedesocial.redesocial.service.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	public UsuarioRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping 	//Estou me referinndo a um metodo Get para vir uma lista de usuario
	public ResponseEntity<List<Usuario>> GetAll(){
		
		return ResponseEntity.ok(repository.findAll()); 					//Chamando o Metodo FindAll para trazer(retornar) uma LISTA de Dados
	}
	
	
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioService.CadastrarUsuario(usuario));
	}
	
	
	@GetMapping("/{id}") 													//Defini que irei receber como um parametro atravez da URL //localhost:8080/usuario/2  <-- o Numero 2 é o parametro
	public ResponseEntity<Usuario> GetById(@PathVariable long id){  			// @PathVariable infoma que o valor virá da URL como paramentro
		
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) 
				.orElse(ResponseEntity.notFound().build()); 				// Metodo Map retorma uma valor do tipo usuario e caso exista ele retorna um objeto do tipo 200 OK e caso não exista ele retorna um badResquest 404
	}
	
	
	
	@PostMapping 															//Metodo Post, iremos postar (inserir) as informações em nosso banco de dados
	public ResponseEntity<Usuario> post(@RequestBody Usuario usuario){  			//@RequestBody -> Informa que o objeto usuario virá via Body (Corpo da requisição)
		
		return ResponseEntity.status(HttpStatus.CREATED)  					//ResponseEntity.status(HttpStatus.CREATED) retorna o status 201 Create para o cliente
				.body(repository.save(usuario));    							//.body(repository.save(turma)) retorna na body -- Corpo o objeto Criado
	}																		//Lembrando que quando o objeto vem sem o ID estamos fazendo uma atualização.
	
	
	
	
	@PutMapping																//Metodo que fará a Atualização nos dados que ja existem na nossa base
	public ResponseEntity<Usuario> Put(@RequestBody Usuario usuario){				//@RequestBody -> Informa que o objeto usuario virá via Body (Corpo da requisição)
		return ResponseEntity.status(HttpStatus.OK) 						//ResponseEntity.status(HttpStatus.OK) retorna o status 200 OK para o cliente
				.body(repository.save(usuario)); 								//.body(repository.save(turma)) retorna na body -- Corpo o objeto Atualizado
	}																		//Lembrando que quando o objeto vem com o ID estamos fazendo uma atualização.
	
	
	
	
	@DeleteMapping("/{id}")													//Metodo que irá deletar os dados do nosso banco
	public void Delete (@PathVariable long id) {							// @PathVariable infoma que o valor virá da URL como paramentro
		repository.deleteById(id);											//O metodo deverá ser void, ou seja não retorna nada
	}
	

}
