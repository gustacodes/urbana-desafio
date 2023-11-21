package com.desafio.urbana.services;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.exceptions.CartaoInexistenteException;
import com.desafio.urbana.exceptions.EmailExistenteException;
import com.desafio.urbana.exceptions.UsuarioInexistenteException;
import com.desafio.urbana.repositories.CartaoRepositoryJPA;
import com.desafio.urbana.repositories.UsuarioRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private CartaoRepositoryJPA cartaoRepositoryJPA;

    @Autowired
    private UsuarioRepositoryJPA usuarioRepositoryJPA;

    public Usuario criar(Usuario usuario) {
        return usuarioRepositoryJPA.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepositoryJPA.findAll();
    }

    public Usuario buscarExistentePorId(Long id) {
        Usuario usuario = usuarioRepositoryJPA.findById(id).get();

        if (usuario == null) {
            throw new UsuarioInexistenteException(id);
        }

        return usuario;
    }

    public void remover(Long id) {
        usuarioRepositoryJPA.deleteById(id);
    }

    private void validarCriacao(Usuario usuario) {
        var usuarioExistente = usuarioRepositoryJPA.email(usuario.getEmail());

        if (usuarioExistente != null) {
            throw new EmailExistenteException();
        }
    }

    private void validarAtualizacao(Long id, Usuario usuario) {
        var usuarioExistente = usuarioRepositoryJPA.email(usuario.getEmail());

        if (usuarioExistente != null && usuarioExistente.getId() != id) {
            throw new EmailExistenteException();
        }
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarExistentePorId(id);

        validarAtualizacao(id, usuario);

        if (usuarioExistente != null) {
            if (usuario.getNome() != null) {
                usuarioExistente.setNome(usuario.getNome());
            }

            if (usuario.getEmail() != null) {
                usuarioExistente.setEmail(usuario.getEmail());
            }

            if (usuario.getSenha() != null) {
                usuarioExistente.setSenha(usuario.getSenha());
            }
        }

        return usuarioRepositoryJPA.save(usuarioExistente);
    }

    public Usuario adicionarNovoCartao(Long id, Cartao cartao) {
        var numeroGerado = new Random();
        int numero = numeroGerado.nextInt(1000000);
        cartao.setNumeroCartao(numero);

        Usuario usuario = buscarExistentePorId(id);
        List<Cartao> novoCartao = new ArrayList<>();
        novoCartao.add(cartao);

        if (usuario != null) {
            usuario.setCartoes(novoCartao);
            cartao.setUsuario(usuario);
            cartaoRepositoryJPA.save(cartao);
            usuarioRepositoryJPA.save(usuario);
        }

        return usuario;
    }

    public Cartao alterarStatusCartao(Long id, Long idCartao) {
        Usuario usuario = buscarExistentePorId(id);
        List<Cartao> cartoes = usuario.getCartoes();
        Optional<Cartao> cartaoOptional = cartoes.stream().filter(card -> card.getId() == idCartao).findFirst();

        if (cartaoOptional.isPresent()) {
            var cartao = cartaoOptional.get();
            cartao.setStatus(!cartao.getStatus());
            return cartaoRepositoryJPA.save(cartao);
        } else {
            throw new CartaoInexistenteException("Cartão inexistente");
        }
    }

    public List<Cartao> listarCartoesPorUsuario(Long id) {
        Usuario usuario = buscarExistentePorId(id);
        return usuario.getCartoes();
    }

}
