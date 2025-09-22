package com.backend.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.backend.blog.security.CustomUserDetailService;
import com.backend.blog.security.JwtAuthenticationEntryPoint;
import com.backend.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
    	http
        .csrf(csrf -> csrf.disable()) // Disable CSRF
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(AppConstants.PUBLIC_URLS).permitAll() // public endpoints
            .anyRequest().authenticated() // Require authentication for all requests
        )
        .exceptionHandling(exception -> 
        exception.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
        )
        .sessionManagement(session -> 
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	
    	http.addFilterBefore(this.jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class);

//    	http.authenticationProvider(daoAuthenticationProvider(this.customUserDetailService));
        return http.build();
	}
    
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//    	auth.userDetailsService(this.customUserDetailService)
//    	                        .passwordEncoder(passwordEncoder());
//    }
    
//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailService customUserDetailService) {
//    	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserDetailService);
//    	daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
//    	return daoAuthenticationProvider;
//    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
    
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailService)
               .passwordEncoder(passwordEncoder());
        return builder.build();
    }

}
 