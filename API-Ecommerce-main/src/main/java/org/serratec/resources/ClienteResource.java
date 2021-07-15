package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.cliente.ClienteCadastroDTO;
import org.serratec.dto.cliente.ClienteCompletoDTO;
import org.serratec.exception.ClienteException;
import org.serratec.metodos.ValidaCPF;
import org.serratec.models.Cliente;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.EnderecoRepository;
import org.serratec.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "API - Clientes do Ecommerce")
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EmailService emailService;
	
	@ApiOperation(value = "Consulta todos os clientes")
	@GetMapping("/cliente/todos")
	public ResponseEntity<?> getTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteCompletoDTO> clientesDtos = new ArrayList<>();
		
		for (Cliente cliente : clientes) {
			clientesDtos.add(new ClienteCompletoDTO(cliente));
		}
		
		return new ResponseEntity<>(clientesDtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Consulta cliente pelo ID do mesmo com detalhamento")
	@GetMapping("/cliente/detalhe/{id}")
	public ResponseEntity<?> getDetalhe(@PathVariable Long id) {
		
		try {
			Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteException("Cliente não encontrado"));
			return new ResponseEntity<>(new ClienteCompletoDTO(cliente), HttpStatus.OK);
			
		} catch (ClienteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Cadastro de um novo cliente")
	@PostMapping("/cliente")
	public ResponseEntity<?> postCliente(@Validated @RequestBody ClienteCadastroDTO clienteDTO) {
		
		Cliente cliente = clienteDTO.toCliente();
		try {		
			  if(!ValidaCPF.isCPF(cliente.getCPF())) return new
			  ResponseEntity<>("CPF inválido!", HttpStatus.BAD_REQUEST);
			 
			
			clienteRepository.save(cliente);
			
			emailService.enviar("Bem vindo", "Seu e-mail foi cadastrado com sucesso!", cliente.getEmail());
			
			return new ResponseEntity<>("Cliente cadastrado com sucesso!", HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			if(clienteRepository.existsByEmail(cliente.getEmail()) && clienteRepository.existsByCPF(cliente.getCPF()))
				return new ResponseEntity<>("Já existe um cliente com este email e CPF", HttpStatus.BAD_REQUEST);
			if(clienteRepository.existsByEmail(cliente.getEmail()))
				return new ResponseEntity<>("Já existe um cliente com este email", HttpStatus.BAD_REQUEST);
			if(clienteRepository.existsByCPF(cliente.getCPF()))
				return new ResponseEntity<>("Já existe um cliente com este CPF", HttpStatus.BAD_REQUEST);
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} 
		catch (MethodArgumentNotValidException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Atualização de um cliente a partir do seu ID")
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> putCliente(@PathVariable Long id, @RequestBody Cliente novo) {
		
		try {
			Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteException("Cliente não encontrado.") );
			
			if(novo.getUserNameCliente() != null && !novo.getUserNameCliente().isBlank())
				cliente.setUserNameCliente(novo.getUserNameCliente());
			if(novo.getNome() != null && !novo.getNome().isBlank())
				cliente.setNome(novo.getNome());
			if(novo.getTelefone() != null && !novo.getTelefone().isBlank())
				cliente.setTelefone(novo.getTelefone());
			
			clienteRepository.save(cliente);
			
			return new ResponseEntity<>(new ClienteCompletoDTO(cliente), HttpStatus.OK);
		} catch (ClienteException e) {
			return new ResponseEntity<>("Erro ao atualizar seu cadastro.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Desativação da conta do cliente")
	@GetMapping("/cliente/desativar/{email}")
	public ResponseEntity<?> getDesativar(@PathVariable String email) {

		try {
			Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(() -> new ClienteException("Cliente não encontrado."));
			cliente.setStatusConta(false);
			clienteRepository.save(cliente);
			return new ResponseEntity<>("Conta desativada com sucesso.", HttpStatus.OK);

		} catch (ClienteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Ativação da conta do cliente")
	@GetMapping("/cliente/ativar/{email}")
	public ResponseEntity<?> getAtivar(@PathVariable String email) {

		try {
			Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(() -> new ClienteException("Cliente não encontrado."));
			cliente.setStatusConta(true);
			clienteRepository.save(cliente);
			return new ResponseEntity<>("Conta ativada com sucesso.", HttpStatus.OK);

		} catch (ClienteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
} 
