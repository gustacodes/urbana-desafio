package com.desafio.urbana.controller;

import com.desafio.urbana.config.TokenService;
import com.desafio.urbana.dto.AutenthicationDTO;
import com.desafio.urbana.dto.LoginResponseDTO;
import com.desafio.urbana.dto.RegistrarDTO;
import com.desafio.urbana.entities.User;
import com.desafio.urbana.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenthicationDTO data) {
        var usuario = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usuario);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid RegistrarDTO registrarDTO) {
        if (this.userRepository.findByLogin(registrarDTO.login()) != null) {

            return ResponseEntity.badRequest().build();
        } else {
            String encripitarSenha = new BCryptPasswordEncoder().encode(registrarDTO.senha());
            var usuario = new User(registrarDTO.login(), encripitarSenha, registrarDTO.role());
            userRepository.save(usuario);

            return ResponseEntity.ok().build();
        }
    }

}