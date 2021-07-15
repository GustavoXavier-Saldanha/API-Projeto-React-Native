package org.serratec.dto.pedido;

import java.util.Optional;

import org.serratec.exception.ProdutosPedidoException;
import org.serratec.models.Produto;
import org.serratec.models.ProdutosPedido;
import org.serratec.repository.ProdutoRepository;

public class PedidoCadastroItemDTO {

	private String codigoProduto;
	private Integer quantidade;
	
	public ProdutosPedido toProdutosPedido(ProdutoRepository pr) {
		ProdutosPedido pp = new ProdutosPedido();
		
		Optional<Produto> produto = pr.findByCodigo(codigoProduto);
		
		if (produto.isEmpty())
			throw new ProdutosPedidoException("Produto não encontrado.");
		
		pp.setProduto(produto.get());
		pp.setQuantidade(quantidade);
		pp.setPreco(produto.get().getPreco());
		return pp;
		
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
	
}
