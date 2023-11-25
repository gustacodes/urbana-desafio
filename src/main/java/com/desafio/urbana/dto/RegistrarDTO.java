package com.desafio.urbana.dto;

import com.desafio.urbana.enums.Role;

public record RegistrarDTO(String login, String senha, Role role) {
}
