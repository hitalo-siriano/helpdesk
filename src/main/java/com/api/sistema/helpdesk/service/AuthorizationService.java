package com.api.sistema.helpdesk.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.sistema.helpdesk.dtos.AuthentaticationDto;
import com.api.sistema.helpdesk.dtos.LoginRespnseDto;
import com.api.sistema.helpdesk.dtos.RegisterUserDto;
import com.api.sistema.helpdesk.models.UserModel;
import com.api.sistema.helpdesk.repositories.UserRepository;

import jakarta.validation.Valid;

@Service
public class AuthorizationService implements UserDetailsService {
   
   @Autowired
   UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        return userRepository.findByLogin(username);
       
    }    
    
}
