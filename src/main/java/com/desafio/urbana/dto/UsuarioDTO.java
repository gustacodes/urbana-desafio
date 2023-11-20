package com.desafio.urbana.dto;

import com.desafio.urbana.entities.Cartao;

import java.util.List;

public class UsuarioDTO{
    private Long id;
    private String nome;
    private String email;
    private List<Cartao> cartoes;

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


