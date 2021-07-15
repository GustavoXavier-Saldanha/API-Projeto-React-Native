package org.serratec.dto.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.dto.cliente.ClienteSimplificadoDTO;
import org.serratec.dto.produto.ProdutoSimplificadoDTO;
import org.serratec.models.Pedido;

public class PedidoDetalhesDTO {

	private String codigoPedido;
	private ClienteSimplificadoDTO cliente;
	private LocalDateTime dataPedido;
	private Double totalPedido;
	private List<ProdutoSimplificadoDTO> itens = new ArrayList<>();
	
	public PedidoDetalhesDTO(Pedido pedido) {
		this.codigoPedido = pedido.getNumeroPedido();
		this.cliente = new ClienteSimplificadoDTO(pedido.getCliente());
		this.dataPedido = pedido.getDataPedido();
		this.totalPedido = pedido.getTotalPedido();
		this.itens = pedido.getProdutos().stream().map(obj -> new ProdutoSimplificadoDTO(obj)).collect(Collectors.toList());
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public ClienteSimplificadoDTO getCliente() {
		return cliente;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public Double getTotalPedido() {
		return totalPedido;
	}

	public List<ProdutoSimplificadoDTO> getItens() {
		return itens;
	}

	
}
