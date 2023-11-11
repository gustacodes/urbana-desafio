package com.desafio.urbana.entities;

import com.desafio.urbana.enums.TipoCartao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Cartao {

    private Long id;
    private Long numeroCartao;
    private String nome;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private TipoCartao tipoCartao;
    private Usuario usuario;

    public Cartao(Long id, Long numeroCartao, String nome, boolean status, TipoCartao tipoCartao) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.nome = nome;
        this.status = status;
        this.tipoCartao = tipoCartao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(Long numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TipoCartao getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(TipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }
}
