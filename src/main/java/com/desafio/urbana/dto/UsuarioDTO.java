package com.desafio.urbana.dto;

import com.desafio.urbana.entities.Cartao;

import java.util.ArrayList;
import java.util.List;

public record UsuarioDTO(Long id,
        String nome,
        String email,
        String senha,
        List<Cartao>cartao
) {
}
