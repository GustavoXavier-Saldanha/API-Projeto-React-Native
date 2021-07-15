package org.serratec.dto.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.serratec.exception.PedidoException;
import org.serratec.models.Cliente;
import org.serratec.models.Pedido;
import org.serratec.models.ProdutosPedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.ProdutoRepository;

public class PedidoCadastroDTO {

	private String email;
	private List<PedidoCadastroItemDTO> itens = new ArrayList<>();

	public Pedido toPedido(ClienteRepository cr, ProdutoRepository pr) throws PedidoException {
		Pedido pedido = new Pedido();
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setNumeroPedido(this.gerarNovoCodigoVenda());
		
		Cliente cliente = cr.findByEmail(email).orElseThrow(() -> new PedidoException("E-mail não informado."));
		pedido.setCliente(cliente);
		
		for (PedidoCadastroItemDTO pedidoCadastroItem : itens) {
			ProdutosPedido produtoPedido = pedidoCadastroItem.toProdutosPedido(pr);
			produtoPedido.setPedido(pedido);
			pedido.getProdutos().add(produtoPedido);
		}
		
		pedido.setValorTotal(pedido.getTotalPedido());
		
		return pedido;
	}

	private String gerarNovoCodigoVenda() {

		LocalDateTime agora = LocalDateTime.now();
		Random randomico = new Random();
		String codigo = "v";
		codigo += agora.getYear();
		codigo += agora.getMonth();
		codigo += agora.getDayOfMonth();
		codigo += agora.getHour();
		codigo += agora.getMinute();
		codigo += agora.getSecond();

		for (int i = 0; i < 10; i++) {
			codigo += randomico.nextInt(10);
		}

		return codigo;
	}
	

	public String getEmail() {
		return email;
	}

	public List<PedidoCadastroItemDTO> getItens() {
		return itens;
	}
	
}
