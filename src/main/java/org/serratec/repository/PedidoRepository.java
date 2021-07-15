package org.serratec.repository;

import java.util.Optional;

import org.serratec.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	Optional<Pedido> findByNumeroPedido(String numeroPedido);

}
