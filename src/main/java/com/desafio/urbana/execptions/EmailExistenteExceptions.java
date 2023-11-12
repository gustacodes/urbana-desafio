package com.desafio.urbana.execptions;

import jakarta.persistence.NonUniqueResultException;

public class EmailExistenteExceptions extends NonUniqueResultException {

    public EmailExistenteExceptions(String message) {
        super(message);
    }
}
