package com.desafio.urbana.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.desafio.urbana.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api") //Emissor ou criador do Token
                    .withSubject(user.getLogin()) //Informa quem é o usuário que está recebendo o Token
                    .withExpiresAt(gerarExpiracaoDoToken()) //Duração do Token
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o Token ", e);
        }
    }

    public String validarToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)//Verifica o Token
                    .getSubject(); //Pega o login que estava no Token

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    //Método para determinar a validade do Token
    private Instant gerarExpiracaoDoToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
