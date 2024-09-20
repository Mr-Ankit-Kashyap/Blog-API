package com.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.securities.JwtAuthenticationEntryPoint;
import com.api.securities.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private JwtAuthenticationEntryPoint point;

	private JwtAuthenticationFilter filter;

	private UserDetailsService userDetailsService;

	private PasswordEncoder passwordEncoder;

	public SecurityConfig(JwtAuthenticationEntryPoint point, JwtAuthenticationFilter filter,
			UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.point = point;
		this.filter = filter;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/login").permitAll()
						.requestMatchers("/api/auth/register").permitAll().anyRequest().authenticated())

				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

	public DaoAuthenticationProvider doDaoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;

	}

}
