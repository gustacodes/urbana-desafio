package com.desafio.urbana.entities;

import com.desafio.urbana.enums.TipoCartao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_cartao")
    private Integer numeroCartao;
    @NotBlank(message = "Selecione o nome do cartão")
    private String nome;
    private Boolean status = true;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cartao")
    private TipoCartao tipoCartao;
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

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
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

    public void setTipoCartao(TipoCartao tipo_cartao) {
        this.tipoCartao = tipo_cartao;
    }
}
