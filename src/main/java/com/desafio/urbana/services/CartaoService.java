package com.desafio.urbana.services;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.exceptions.CartaoInexistenteException;
import com.desafio.urbana.repositories.CartaoRepository;
import com.desafio.urbana.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cartao> listarCartoes() {
        return cartaoRepository.listarCartoes();
    }

    public Cartao buscarCartaoId(Long id) {
        try {
            return cartaoRepository.buscarCartaoId(id);
        } catch (Exception e) {
            throw new CartaoInexistenteException("Cartão não encontrado! Verifique o e-mail digitado.");
        }
    }

    public Cartao alterarStatusCartao(Long id, Long idCartao) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        List<Cartao> cartoes = usuario.getCartoes();
        Optional<Cartao> cartaoOptional = cartoes.stream().filter(card -> card.getId() == idCartao).findFirst();

        if (cartaoOptional.isPresent()) {
            var cartao = cartaoOptional.get();
            return cartaoRepository.alterarStatus(cartao.getId());
        } else {
            throw new CartaoInexistenteException("Cartão inexistente");
        }
    }

    public void associarCartoesAoUsuario(Long usuarioId, List<Cartao> cartoes) {
        cartaoRepository.associarCartoesAoUsuario(usuarioId, cartoes);
    }

    public void persistirCartao(Cartao cartao, Long usuarioId) {
        cartaoRepository.persistirCartao(cartao, usuarioId);
    }

    public void associarUsuarioCartao(Long usuarioId, Long cartaoId) {
        cartaoRepository.associarUsuarioCartao(usuarioId, cartaoId);
    }

    public void removerCartao(Long idCartao) {
        cartaoRepository.removerCartao(idCartao);
    }

}
