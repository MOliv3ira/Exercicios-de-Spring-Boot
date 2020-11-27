package br.com.gft.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.estudo.api.model.Lancamento;
import br.com.gft.estudo.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
