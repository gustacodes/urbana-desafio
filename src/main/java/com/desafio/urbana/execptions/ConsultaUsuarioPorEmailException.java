package com.desafio.urbana.execptions;

import jakarta.persistence.NoResultException;

public class ConsultaUsuarioPorEmailException extends NoResultException {

    public ConsultaUsuarioPorEmailException(String message) {
        super(message);
    }
}
