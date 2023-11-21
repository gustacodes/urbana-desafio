package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
    Usuario email(String email);
}
