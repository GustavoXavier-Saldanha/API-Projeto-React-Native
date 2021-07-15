package org.serratec.dto.categoria;

import org.serratec.models.Categoria;

public class CategoriaSimplificadaDTO {

	private String nome;
	
	public CategoriaSimplificadaDTO(Categoria categoria) {
		this.nome = categoria.getNome();
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return String.format("%s", nome);
	}
}
