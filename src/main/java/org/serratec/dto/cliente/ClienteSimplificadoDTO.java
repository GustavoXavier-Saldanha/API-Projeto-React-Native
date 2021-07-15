package org.serratec.dto.cliente;

import org.serratec.models.Cliente;

public class ClienteSimplificadoDTO {
	
	private String nome;
	private String email;
	
	public ClienteSimplificadoDTO(Cliente cliente) {
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}
	
	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}
	
}
