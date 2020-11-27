package br.com.gft.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.estudo.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	

}
