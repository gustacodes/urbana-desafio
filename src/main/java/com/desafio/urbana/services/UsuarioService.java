package com.desafio.urbana.services;

import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criar(Usuario usuario) {
        return usuarioRepository.criarUsuario(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listar();
    }

    public Usuario buscar(String email) {
        return usuarioRepository.buscar(email);
    }

}
