package com.desafio.urbana.exceptions;

import jakarta.persistence.NoResultException;

public class ConsultaUsuarioPorEmailException extends NoResultException {

    public ConsultaUsuarioPorEmailException(String message) {
        super(message);
    }
}
