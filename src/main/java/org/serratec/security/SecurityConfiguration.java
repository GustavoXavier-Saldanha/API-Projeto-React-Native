package org.serratec.security;

import org.serratec.repository.ClienteRepository;
import org.serratec.security.filter.TokenAuthenticationFilter;
import org.serratec.security.service.AuthenticationService;
import org.serratec.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
    //Configurations for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuration for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers(HttpMethod.POST, "/auth").permitAll()
        	.antMatchers(HttpMethod.POST, "/cliente").permitAll()
        	.antMatchers(HttpMethod.POST, "/categoria").permitAll()
        	.antMatchers(HttpMethod.POST, "/produto").permitAll()
        	.antMatchers(HttpMethod.GET, "/cliente").permitAll()
        	.antMatchers(HttpMethod.PUT, "/produto/{id}").permitAll()
        	.antMatchers(HttpMethod.PUT, "/cliente").permitAll()
        	.antMatchers(HttpMethod.DELETE, "/cliente").permitAll()
        	.antMatchers(HttpMethod.DELETE, "/produto/{id}").permitAll()
         	.antMatchers(HttpMethod.POST, "/pedido").permitAll()
         	.antMatchers(HttpMethod.POST, "/pedido").permitAll()
        	.antMatchers(HttpMethod.GET, "/pedido/todos").permitAll()
        	.antMatchers(HttpMethod.PUT, "/pedido/detalhado/{numeroPedido}").permitAll()
        	.antMatchers(HttpMethod.DELETE, "/pedido/excluir/{numeroPedido}").permitAll()
        	.antMatchers(HttpMethod.GET, "/categoria/todas").permitAll()
        	.antMatchers(HttpMethod.GET, "/categoria/{nome}").permitAll()
        	.antMatchers(HttpMethod.GET, "/produto/todos").permitAll()
        	.antMatchers(HttpMethod.GET, "/produto/{nome}").permitAll()
        	.antMatchers(HttpMethod.GET, "/produto/imagem/{id}").permitAll()   
			.anyRequest().authenticated()
			.and().cors().and().csrf().disable()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and().addFilterBefore(new TokenAuthenticationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }

     
    
    @Override
	public void configure(WebSecurity web) throws Exception{
		//web.ignoring().antMatchers("/css/*", "/img/", "/js/*");
		web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
	}
}