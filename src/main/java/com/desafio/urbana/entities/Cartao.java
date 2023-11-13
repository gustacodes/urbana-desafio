package com.desafio.urbana.entities;

import com.desafio.urbana.enums.TipoCartao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numero_cartao;
    @NotBlank(message = "Selecione o nome do cartão")
    private String nome;
    private Boolean status = true;
    @Enumerated(EnumType.STRING)
    private TipoCartao tipo_cartao;
    @JsonIgnore
    @ManyToOne
    @Valid
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

    public int getNumero_cartao() {
        return numero_cartao;
    }

    public void setNumero_cartao(int numero_cartao) {
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
