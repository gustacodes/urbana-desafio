package com.desafio.urbana.controller;

import com.desafio.urbana.dto.CartaoDTO;
import com.desafio.urbana.dto.UsuarioCriarDTO;
import com.desafio.urbana.dto.UsuarioDTO;
import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.services.CartaoService;
import com.desafio.urbana.services.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity criar(@RequestBody @Valid UsuarioCriarDTO usuarioCriarDTO) {
        var usuarioNovo = usuarioService.criar(mapper.map(usuarioCriarDTO, Usuario.class));
        UsuarioDTO usuarioDTO = mapper.map(usuarioNovo, UsuarioDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity listar() {
        var listar = usuarioService.listar();
        List<UsuarioDTO> usuariosDTO = Arrays.asList(mapper.map(listar, UsuarioDTO[].class));
        return ResponseEntity.ok().body(usuariosDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody UsuarioCriarDTO usuarioCriarDTO) {
        var usuarioAtualizado = usuarioService.atualizar(id, mapper.map(usuarioCriarDTO, Usuario.class));
        UsuarioDTO usuarioDTO = mapper.map(usuarioAtualizado, UsuarioDTO.class);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/cartoes")
    public ResponseEntity criarCartao(@PathVariable Long id, @RequestBody @Valid Cartao cartao) {
        return ResponseEntity.ok().body(usuarioService.adicionarNovoCartao(id, cartao));
    }

    @GetMapping("/{id}/cartoes")
    public ResponseEntity listarCartoes(@PathVariable Long id) {
        List<Cartao> cartoesDoUsuario = usuarioService.listarCartoesPorUsuario(id);
        List<CartaoDTO> cartoesDTO = Arrays.asList(mapper.map(cartoesDoUsuario, CartaoDTO[].class));
        return ResponseEntity.ok().body(cartoesDoUsuario);
    }

    @PatchMapping("{id}/cartoes/{idCartao}/status")
    public ResponseEntity alterarStatusCartao(@PathVariable Long id, @PathVariable Long idCartao) {
        return ResponseEntity.ok().body(cartaoService.alterarStatusCartao(id, idCartao));
    }

    @DeleteMapping("{id}/cartoes/{idCartao}")
    public ResponseEntity removerCartao(@PathVariable Long idCartao) {
        cartaoService.removerCartao(idCartao);
        return ResponseEntity.noContent().build();
    }

}