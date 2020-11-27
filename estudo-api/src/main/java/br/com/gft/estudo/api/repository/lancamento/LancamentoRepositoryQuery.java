package br.com.gft.estudo.api.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.gft.estudo.api.model.Lancamento;
import br.com.gft.estudo.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filter(LancamentoFilter lancamentoFilter, Pageable pageable);

}
