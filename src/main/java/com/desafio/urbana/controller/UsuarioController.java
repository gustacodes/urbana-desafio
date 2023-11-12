package com.desafio.urbana.controller;

import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.repositories.UsuarioRepository;
import com.desafio.urbana.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscar(@PathVariable String email) {
        return ResponseEntity.ok().body(usuarioRepository.buscar(email));
    }
}
