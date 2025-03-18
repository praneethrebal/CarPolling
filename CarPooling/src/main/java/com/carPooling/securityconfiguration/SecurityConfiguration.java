package com.carPooling.securityconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.carPooling.filter.JWTFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JWTFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		
		return http
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)	
			.authorizeHttpRequests(req->req.requestMatchers("/login","/register","/error").permitAll()
											.requestMatchers("/passeanger/**").hasRole("PASSENGER")
											.requestMatchers("/driver/**").hasRole("DRIVER")
											.anyRequest().authenticated())
			.sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf->csrf.disable())
			.formLogin(form->form.disable())
			.httpBasic(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
		    .build();
	}
	
	
	@Bean 
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
	

}
