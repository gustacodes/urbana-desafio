package com.desafio.urbana.exceptions;

public class UsuarioInexistenteException extends RuntimeException {

    public UsuarioInexistenteException(Long id) {
        super("Usuário não encontrado: " + id);
    }
}
