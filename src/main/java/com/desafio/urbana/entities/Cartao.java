package com.desafio.urbana.entities;

import com.desafio.urbana.enums.TipoCartao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

@Table(name = "Cartao")
@Entity(name = "Cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger numeroCartao;
    @NotBlank(message = "Selecione o cartão desejado")
    private String nome;
    private boolean status;
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Selecione o tipo do cartão")
    private TipoCartao cartao;

    public Cartao(Long id, BigInteger numeroCartao, String nome, boolean status, TipoCartao cartao) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.nome = nome;
        this.status = status;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(BigInteger numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TipoCartao getCartao() {
        return cartao;
    }

    public void setCartao(TipoCartao cartao) {
        this.cartao = cartao;
    }
}
