package br.com.gft.desafio.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.desafio.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	public List<Produto> findAllByNomeContainingIgnoreCase(String nome);
	
	public List<Produto> findAllByOrderByNomeAsc();
	
	public List<Produto> findAllByOrderByNomeDesc();

}
