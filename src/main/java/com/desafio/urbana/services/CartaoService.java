package com.desafio.urbana.services;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.exceptions.CartaoInexistenteException;
import com.desafio.urbana.exceptions.ConsultaUsuarioPorEmailException;
import com.desafio.urbana.repositories.CartaoRepository;
import com.desafio.urbana.repositories.CartaoRepositoryJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    @Autowired
    private CartaoRepositoryJPA cartaoRepositoryJPA;

//    public List<Cartao> listarCartoes() {
//        return cartaoRepository.listarCartoes();
//    }
    //
//    public Cartao buscarCartaoId(Long id) {
//        try {
//            return cartaoRepository.buscarCartaoId(id);
//        } catch (Exception e) {
//            throw new CartaoInexistenteException("Cartão não encontrado! Verifique o e-mail digitado.");
//        }
//    }

//    public void persistirCartao(Cartao cartao, Long usuarioId) {
//        cartaoRepository.persistirCartao(cartao, usuarioId);
//    }
    //
//    public void associarCartoesAoUsuario(Long usuarioId, List<Cartao> cartoes) {
//        cartaoRepository.associarCartoesAoUsuario(usuarioId, cartoes);
//    }
//
//
//    public void associarUsuarioCartao(Long usuarioId, Long cartaoId) {
//        cartaoRepository.associarUsuarioCartao(usuarioId, cartaoId);
//    }

//
//    public void removerCartao(Long idCartao) {
//        cartaoRepository.removerCartao(idCartao);
//    }

    public void persistirCartao(Cartao cartao) {
        cartaoRepositoryJPA.save(cartao);
    }

    public List<Cartao> listarCartoes() {
        return cartaoRepositoryJPA.findAll();
    }

    public Cartao buscarCartaoId(Long id) {
        return cartaoRepositoryJPA.findById(id).get();
    }

    public void removerCartao(Long idCartao) {
        cartaoRepositoryJPA.deleteById(idCartao);
    }

}
