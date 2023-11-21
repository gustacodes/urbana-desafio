package com.desafio.urbana.controller;

import com.desafio.urbana.dto.CartaoDTO;
import com.desafio.urbana.services.CartaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
@CrossOrigin(origins = "http://localhost:4200/")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity listarCartoes() {
        var listaDeCartoes = cartaoService.listarCartoes();
        List<CartaoDTO> lista = Arrays.asList(mapper.map(listaDeCartoes, CartaoDTO[].class));
        return ResponseEntity.ok().body(lista);
    }
}