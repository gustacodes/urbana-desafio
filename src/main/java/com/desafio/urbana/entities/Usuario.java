package com.desafio.urbana.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome obrigatório")
    private String nome;
    @NotBlank(message = "E-mail obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;
    @NotBlank(message = "Senha obrigatória")
    private String senha;
    @OneToMany
    private List<Cartao> cartao;

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
