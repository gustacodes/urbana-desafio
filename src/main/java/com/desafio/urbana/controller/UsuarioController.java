package com.desafio.urbana.controller;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.services.CartaoService;
import com.desafio.urbana.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody @Valid Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String email) {
        return ResponseEntity.ok().body(usuarioService.buscar(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(id, usuario));
    }

    @PutMapping("/novo/{id}")
    public ResponseEntity adicionaNovoCartao(@PathVariable Long id, @RequestBody @Valid Cartao cartao, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok().body(usuarioService.adicionaNovoCartao(id, cartao));
    }

    @PutMapping("/cartao/status/{id}/{numero}")
    public ResponseEntity StatusCartao(@PathVariable Long id, @PathVariable Integer numero) {
        return ResponseEntity.ok().body(usuarioService.StatusCartao(id, numero));
    }

    @DeleteMapping("/remover-cartao/{id}")
    public ResponseEntity removerCartao(@PathVariable Long id) {
        cartaoService.removerCartao(id);
        return ResponseEntity.noContent().build();
    }

}