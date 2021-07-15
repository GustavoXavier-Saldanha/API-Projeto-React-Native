package org.serratec.dto.pedido;

import org.serratec.exception.PedidoException;
import org.serratec.models.Pagamento;
import org.serratec.models.Pedido;
import org.serratec.repository.PedidoRepository;

public class PedidoFinalizarDTO {

	private String numeroPedido;
	private Pagamento formaPagamento;
	
	public Pedido toPedido(PedidoRepository pedidoRepository) throws PedidoException {
		
		Pedido pedido = pedidoRepository.findByNumeroPedido(this.numeroPedido).orElseThrow(() -> new PedidoException("Pedido não encontrado."));
		return pedido;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public Pagamento getFormaPagamento() {
		return formaPagamento;
	}

	
	
}
