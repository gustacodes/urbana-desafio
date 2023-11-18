package com.desafio.urbana.exceptions;

public class EmailExistenteException extends RuntimeException {

    public EmailExistenteException() {
        super("E-mail existente no sistema.");
    }
}
