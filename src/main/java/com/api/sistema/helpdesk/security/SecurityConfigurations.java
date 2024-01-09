package com.api.sistema.helpdesk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
   @Autowired
   SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{


        return httpSecurity
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions().disable())
            .authorizeHttpRequests(authority -> authority
                                    .requestMatchers(HttpMethod.GET,"/api/v1/ticket/all/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.GET,"/api/v1/ticket/all/technician/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/v1/ticket/update/status/closed/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/v1/ticket/update/status/open/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT,"/api/v1/ticket/update/status/atende/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE,"/api/v1/ticket/**").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()                                    
                                    .requestMatchers(HttpMethod.POST,"/api/v1/auth/register").permitAll()
                                     .requestMatchers("/h2-database/**").permitAll()
                                     .requestMatchers("/error**").anonymous()
                                    .anyRequest().authenticated())
                 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)                  
            .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
           return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
