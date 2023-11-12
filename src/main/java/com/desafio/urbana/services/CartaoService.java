package com.desafio.urbana.services;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.repositories.CartaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void associarCartoesAoUsuario(Long usuarioId, List<Cartao> cartoes) {
        cartaoRepository.associarCartoesAoUsuario(usuarioId, cartoes);
    }

    public void persistirCartao(Cartao cartao, Long usuarioId) {
        cartaoRepository.persistirCartao(cartao, usuarioId);
    }

    public void associarUsuarioCartao(Long usuarioId, Long cartaoId) {
        cartaoRepository.associarUsuarioCartao(usuarioId, cartaoId);
    }

}
