package org.serratec.security.service;

import java.util.Optional;

import org.serratec.models.Cliente;
import org.serratec.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Cliente> optional = repository.findByEmail(username);
		
		if(optional.isPresent()) {
			return (UserDetails) optional.get();
		}
		
		throw new UsernameNotFoundException("User not found");
	}	
	
	public Cliente getCliente() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String corrente = authentication.getName();
		
		return (Cliente)loadUserByUsername(corrente);
	}

}
