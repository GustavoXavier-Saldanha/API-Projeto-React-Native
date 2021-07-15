package org.serratec.dto.produto;

import org.serratec.dto.categoria.CategoriaSimplificadaDTO;
import org.serratec.models.ProdutosPedido;

public class ProdutoSimplificadoDTO {

	private String codigo;
	private String nome;
	private Double preco;
	private Integer quantidade;
	private CategoriaSimplificadaDTO categoria;
	
	public ProdutoSimplificadoDTO(ProdutosPedido obj) {
		this.codigo = obj.getProduto().getCodigo();
		this.nome = obj.getProduto().getNome();
		this.preco = obj.getProduto().getPreco();
		this.quantidade = obj.getQuantidade();
		this.categoria = new CategoriaSimplificadaDTO(obj.getProduto().getCategoria());
	}

	public String getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public Double getPreco() {
		return preco;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public CategoriaSimplificadaDTO getCategoria() {
		return categoria;
	}

	@Override
	public String toString() {
		return String.format("Nome: %s\nCódigo: %s\nPreço: %s\nQuantidade: %d\nCategoria: %s\n\n",nome,codigo,preco,quantidade,categoria);
	}
	
}
