package com.minharedesocial.redesocial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minharedesocial.redesocial.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public List<Usuario>findAllByNomeContainingIgnoreCase(String nome);
	
	public Optional<Usuario> findByEmailAndSenha(String email, String senha);
	
	public Optional<Usuario>findByEmail(String email);
}
