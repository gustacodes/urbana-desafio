package com.desafio.urbana.entities;

import java.util.List;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private List<Cartao> cartao;

    public Usuario(String nome, String email, String senha, List<Cartao> cartao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cartao = cartao;
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
