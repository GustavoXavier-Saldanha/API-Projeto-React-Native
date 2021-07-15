package org.serratec.repository;

import java.util.Optional;

import org.serratec.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByEmail(String email);

	Optional<Cliente> findByEmail(String email);

	boolean existsByCPF(String cpf);

}
