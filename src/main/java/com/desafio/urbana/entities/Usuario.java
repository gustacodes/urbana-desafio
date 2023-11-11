package com.desafio.urbana.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Table(name = "Usuario")
@Entity(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Insira seu nome")
    private String nome;
    @NotBlank(message = "Insira seu e-mail")
    private String email;
    @NotBlank(message = "Insira sua senha")
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
