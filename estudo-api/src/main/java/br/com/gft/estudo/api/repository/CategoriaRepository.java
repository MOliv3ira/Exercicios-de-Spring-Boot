package br.com.gft.estudo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.estudo.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
