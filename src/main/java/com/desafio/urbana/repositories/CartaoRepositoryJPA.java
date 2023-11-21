package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepositoryJPA extends JpaRepository<Cartao, Long> {

}
