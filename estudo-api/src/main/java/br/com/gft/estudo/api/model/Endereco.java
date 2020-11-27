package br.com.gft.estudo.api.model;

import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModelProperty;

@Embeddable
public class Endereco {
	
	@ApiModelProperty(example = "Rua do Abacaxi")
	private String logradouro;
	
	@ApiModelProperty(example = "10")
	private String numero;
	
	@ApiModelProperty(example = "Apto 302")
	private String complemento;
	
	@ApiModelProperty(example = "Morumbi")
	private String bairro;
	
	@ApiModelProperty(example = "11.400-12")
	private String cep;
	
	@ApiModelProperty(example = "Uberlândia")
	private String cidade;
	
	@ApiModelProperty(example = "MG")
	private String estado;
	
	
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	

}
