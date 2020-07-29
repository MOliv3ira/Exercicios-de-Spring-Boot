package com.exercicio.turma.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;

import com.sun.istack.NotNull;

@Entity //Estou conectando a minha Model com a minha tabela e seguindo a estrutura de dados
@Table(name = "turma") // estou nomeando a minha tabela como tb_turma
public class Turma {
	
	@Id //Estou definindo o atributo como Id la na minha base de dados
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Defenido como auto-Increment la na minha base
	private long id;
	
	@NotNull   // Não estou permitindo valores nulos na coluna descricao em meu banco
	private String turma;
	
	@NotNull
	private boolean ativo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
}
