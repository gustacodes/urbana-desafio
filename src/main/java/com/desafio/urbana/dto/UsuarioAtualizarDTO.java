package com.desafio.urbana.dto;

import com.desafio.urbana.entities.Cartao;

import java.util.List;

public record UsuarioAtualizarDTO(
        String nome,
        String email,
        String senha
) {
}
