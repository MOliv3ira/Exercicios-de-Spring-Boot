package com.exercicio.objetivos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/objetivos")

public class ObjetivosController {
	
	@GetMapping
	public String objetivos() {

		return "\tEstou com grandes expectativas para essa semana que se inicia com o aprendizado de Spring Boot, acredito que por ser um conteúdo novo o começo não vai ser fácil, mas com muita persistência e atenção aos detalhes vou conseguir aprender e resolver os exercícios.\r\n" + 
				"\tObjetivos da semana:\r\n" + 
				"\tAbsorver o novo conteúdo (Spring Boot);\r\n" + 
				"\tPraticar bastante com exercícios;\r\n" + 
				"\tColocar em prática no projeto integrador. \r\n";
	}

}
