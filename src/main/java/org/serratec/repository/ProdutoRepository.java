package org.serratec.repository;

import java.util.Optional;

import org.serratec.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByNome(String nome);

	Optional<Produto> findByCodigo(String codigoProduto);


}
