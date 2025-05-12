package com.blo.sales.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("!test")
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
	    .authorizeHttpRequests(auth -> auth
	        .requestMatchers("/api/v1/users/actions/login").permitAll()
	        .requestMatchers("/api/v1/users/mgmt/actions/**").hasRole("ROOT") // protegidos con token ROOT
	        .requestMatchers("/api/v1/products/mgmt/**").hasRole("ROOT") // protegidos con token ROOT
	        .requestMatchers("/api/v1/**").authenticated() // protegidos con token común
	        .anyRequest().denyAll()
	    )
	    .addFilterBefore(new JwtAuthFilterRoot(), UsernamePasswordAuthenticationFilter.class)
	    .addFilterBefore(new JwtAuthFilterCommons(), JwtAuthFilterRoot.class);
		
    return http.build();
	}
}
