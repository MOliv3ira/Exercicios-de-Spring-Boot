package br.com.farmacia10.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.farmacia10.farmacia.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public List<Usuario>findAllByNomeContainingIgnoreCase(String nome);
	
	public Optional<Usuario> findByEmailAndSenha(String email, String senha);
	
	public Optional<Usuario>findByEmail(String email);
	
}
