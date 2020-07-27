package com.exercicio.softskills.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/softskills")

public class SoftskillsController {
	
	@GetMapping
	public String Softskills() {

		return"Utilizei como mentalidades, persistência para não desistir quando ocorrer um erro no desenvolvimento de exercícios,"
			+ " devo parar e avaliar quais foram meus erros e depois continuar a fazer as atividades de forma correta."
			+ " Em mentalidade de crescimento eu deixo claro que através desse conteúdo novo que estou aprendendo vou poder atingir novos níveis no desenvolvimento de software."
			+ " E como habilidade usei atenção aos detalhes por ser um conteúdo novo que estou aprendendo devo manter a calma e prestar bastante atenção nas aulas e no meu código.";

	}
}
