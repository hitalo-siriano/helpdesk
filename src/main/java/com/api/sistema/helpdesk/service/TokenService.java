package com.api.sistema.helpdesk.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import com.api.sistema.helpdesk.models.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel user){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token =JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(user.getLogin())
                            .withExpiresAt(genExpiresDate())
                            .sign(algorithm);
            return token;
        } catch (JWTCreationException jwtCreationException) {
            throw new RuntimeException("Erro generate toke",jwtCreationException);
            // TODO: handle eretuxception
        }
    }


    public String validateToken(String token){
        try {
              Algorithm algorithm = Algorithm.HMAC256(secret);
              return JWT.require(algorithm)
                                   .withIssuer("auth-api")
                                   .build()
                                   .verify(token)
                                   .getSubject();
                                   
            
        } catch (JWTVerificationException jwtVerificationException) {

            return "";
            // TODO: handle exception
        }
    }
    private Instant genExpiresDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    
}
