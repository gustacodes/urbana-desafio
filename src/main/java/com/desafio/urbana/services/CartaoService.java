package com.desafio.urbana.services;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.repositories.CartaoRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepositoryJPA cartaoRepositoryJPA;

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
