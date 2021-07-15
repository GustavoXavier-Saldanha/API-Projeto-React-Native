package org.serratec.dto.pedido;

import org.serratec.exception.PedidoException;
import org.serratec.models.Pedido;
import org.serratec.repository.PedidoRepository;

public class PedidoAtualizarItemDTO {
	private String numeroPedido;
	private String codigoProduto;
	private Integer quantidade;
	
	public Pedido toPedido(PedidoRepository pr) throws PedidoException {
		
		Pedido pedido = pr.findByNumeroPedido(this.numeroPedido).orElseThrow(() -> new PedidoException("Pedido não encontrado"));
		return pedido;
	}
	
	public String getNumeroPedido() {
		return numeroPedido;
	}
	public String getCodigoProduto() {
		return codigoProduto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	
}
