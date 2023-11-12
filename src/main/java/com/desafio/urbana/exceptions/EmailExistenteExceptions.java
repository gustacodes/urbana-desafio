package com.desafio.urbana.exceptions;

import jakarta.persistence.NonUniqueResultException;

public class EmailExistenteExceptions extends NonUniqueResultException {

    public EmailExistenteExceptions(String message) {
        super(message);
    }
}
