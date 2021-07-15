package org.serratec.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.serratec.models.Endereco;

public class EnderecoCadastroSimplesDTO {

	@NotNull
	@NotBlank
	private String cep;
	
	@Column(nullable = false, length = 10)
	private String numeroResidencia;
	
	private String complemento;
	
	public Endereco toEndereco() {
		Endereco e = new Endereco();
		
		e.setCep(this.cep);
		e.setNumeroResidencia(this.numeroResidencia);
		e.setComplemento(this.complemento);
		
		return e;
	}

	public String getCep() {
		return cep;
	}

	public String getNumeroResidencia() {
		return numeroResidencia;
	}

	public String getComplemento() {
		return complemento;
	}
	
}
