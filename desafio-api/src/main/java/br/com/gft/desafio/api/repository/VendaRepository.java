package br.com.gft.desafio.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.desafio.api.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{
	
//	public List<Venda> findAllByNomeContainingIgnoreCase(String nome);

}
