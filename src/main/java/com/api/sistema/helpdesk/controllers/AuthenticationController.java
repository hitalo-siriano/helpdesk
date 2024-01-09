package com.api.sistema.helpdesk.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.api.sistema.helpdesk.dtos.AuthentaticationDto;
import com.api.sistema.helpdesk.dtos.LoginRespnseDto;
import com.api.sistema.helpdesk.dtos.RegisterUserDto;
import com.api.sistema.helpdesk.models.UserModel;
import com.api.sistema.helpdesk.repositories.UserRepository;
import com.api.sistema.helpdesk.service.AuthorizationService;
import com.api.sistema.helpdesk.service.TokenService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthentaticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (UserModel)this.repository.findByLogin(data.login());
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginRespnseDto(token,user.getIdUser()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDto data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.name(), data.login() , data.email(), encryptedPassword ,data.Job(), data.department(), data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
    
}
