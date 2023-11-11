package com.desafio.urbana.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Cartao> cartao = new ArrayList<>();

    public Usuario(Long id, String nome, String email, String senha, List<Cartao> cartao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Cartao> getCartao() {
        return cartao;
    }

    public void setCartao(List<Cartao> cartao) {
        this.cartao = cartao;
    }
}
