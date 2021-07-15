package org.serratec.dto.cliente;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.serratec.models.Cliente;
import org.serratec.models.Endereco;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteCadastroDTO {
	
	@NotNull
	@NotBlank
	@Email
	@Column(length = 64)
	private String email;
	
	@NotNull
	@NotBlank
	@Column(length = 20)
	@Size(min = 5, max = 20)
	private String username;
	
	@NotNull
	@NotBlank
	@Column(length = 255)
	@Size(min = 8, max = 255)
	private String senha;
	
	@NotNull
	@NotBlank
	@Column(length = 80)
	@Size(min = 5, max = 80)
	private String nome;
	
	@NotNull
	@NotBlank
	@Column(length = 11)
	private String cpf;
	
	@NotNull
	@NotBlank
	@Column(length = 20)
	private String telefone;
	
	@NotNull
	private LocalDate dataNascimento;
	
	private Optional<Endereco> endereco = Optional.empty();

	
	public Cliente toCliente() {

		Cliente c = new Cliente();
		c.setEmail(this.email);
		c.setUserNameCliente(this.username);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.senha);
		
		c.setSenha(senhaCodificada);
		
		c.setNome(this.nome);
		c.setCPF(this.cpf);
		c.setTelefone(this.telefone);
		c.setDataNascimento(this.dataNascimento);
		if (!this.endereco.isEmpty())
			c.setEndereco(this.endereco.get());
		
		return c;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getSenha() {
		return senha;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public Optional<Endereco> getEndereco() {
		return endereco;
	}
	
	
}
