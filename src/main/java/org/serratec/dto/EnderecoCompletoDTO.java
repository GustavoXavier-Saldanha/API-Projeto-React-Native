package org.serratec.dto;

import org.serratec.models.Endereco;
import org.springframework.web.client.RestTemplate;

public class EnderecoCompletoDTO {

	private String cep;
	private String endereco;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;

	public EnderecoCompletoDTO(Endereco ender) {

		String uri = "https://viacep.com.br/ws/" + ender.getCep() + "/json/";

		RestTemplate rest = new RestTemplate();
		EnderecoViaCepDTO viaCep = rest.getForObject(uri, EnderecoViaCepDTO.class);

		this.endereco = String.format("%s, %s", viaCep.getLogradouro(), ender.getNumeroResidencia());
		this.complemento = ender.getComplemento();
		this.bairro = viaCep.getBairro();
		this.cidade = viaCep.getLocalidade();
		this.estado = viaCep.getUf();
		this.cep = viaCep.getCep();
	}
	
	public String getCep() {
		return cep;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}

}
