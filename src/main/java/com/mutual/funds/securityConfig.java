package com.mutual.funds;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfig {
	
	@Value("${security.jwt.secret-key}")
	private String securityKey;
	

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        		.authorizeHttpRequests( auth -> auth
        				.requestMatchers("/**").permitAll()
        				.anyRequest().authenticated())
        		.csrf(csrf -> csrf.disable())
        		.httpBasic(basic -> basic.disable())
        		.sessionManagement( session -> session.sessionCreationPolicy(
        				SessionCreationPolicy.STATELESS)).build();    
        }
	
}
