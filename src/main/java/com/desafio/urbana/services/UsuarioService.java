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

    public Usuario buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepositoryJPA.findById(id).get();

        if (usuario == null) {
            throw new UsuarioInexistenteException(id);
        }

        return usuario;
    }

    public void removerUsuario(Long id) {
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

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);

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

        Usuario usuario = buscarUsuarioPorId(id);
        cartao.setUsuario(usuario);
        List<Cartao> cartoes = usuario.getCartoes();
        cartoes.add(cartao);

        usuario.setCartoes(cartoes);
        usuarioRepositoryJPA.save(usuario);

        return usuario;
    }

    public Cartao alterarStatusCartao(Long id, Long idCartao) {
        Usuario usuario = buscarUsuarioPorId(id);
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

}
