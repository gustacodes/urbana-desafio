package com.desafio.urbana.entities;

import com.desafio.urbana.enums.TipoCartao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numero_cartao;
    private String nome;
    private boolean status;
    @Enumerated(EnumType.STRING)
    private TipoCartao tipo_cartao;
    @JsonIgnore
    @ManyToOne
    private Usuario usuario;

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

    public Long getNumero_cartao() {
        return numero_cartao;
    }

    public void setNumero_cartao(Long numero_cartao) {
        this.numero_cartao = numero_cartao;
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
        return tipo_cartao;
    }

    public void setTipoCartao(TipoCartao tipo_cartao) {
        this.tipo_cartao = tipo_cartao;
    }
}
