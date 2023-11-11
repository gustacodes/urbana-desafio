package com.desafio.urbana.services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UsuarioService {

    public int gerarNumeroDoCartao() {
        int numeroGerado = ThreadLocalRandom.current().nextInt(10000000, 100000000);
        return numeroGerado;
    }
}
