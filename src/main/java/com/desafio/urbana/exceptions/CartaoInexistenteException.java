package com.desafio.urbana.exceptions;

public class CartaoInexistenteException extends RuntimeException {

    public CartaoInexistenteException(String message) {
        super(message);
    }
}
