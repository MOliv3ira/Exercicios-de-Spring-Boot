package com.exercicio.turma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercicio.turma.model.Turma;

//Esta camada é a camada que ira se comunicar com o banco de dados, quando me refiro a turma estou
//falando de uma objeto/tabela, o atributo long é o tipo primitivo que estou falando do tipo de dado que estou usando
//como ID.

@Repository
public interface TurmaRepository extends JpaRepository<Turma,Long> {
	
	public List<Turma> findAllByTurmaContainingIgnoreCase (String turma);
	
	
}
