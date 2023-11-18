package com.desafio.urbana.exceptions;

import jakarta.persistence.NoResultException;

public class ConsultaUsuarioPorEmailException extends RuntimeException {

    public ConsultaUsuarioPorEmailException(String message) {
        super(message);
    }
}
