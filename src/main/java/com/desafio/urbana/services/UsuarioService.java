package com.desafio.urbana.services;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.exceptions.CartaoInexistenteException;
import com.desafio.urbana.exceptions.EmailExistenteException;
import com.desafio.urbana.exceptions.UsuarioInexistenteException;
import com.desafio.urbana.repositories.CartaoRepository;
import com.desafio.urbana.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Usuario buscarExistentePorId(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioInexistenteException(id);
        }

        return usuario;
    }

    public Usuario criar(Usuario usuario) {
        validarCriacao(usuario);
        return usuarioRepository.persistirUsuario(usuario);
    }

    private void validarCriacao(Usuario usuario) {
        var usuarioExistente = usuarioRepository.buscarPorEmail(usuario.getEmail());

        if (usuarioExistente != null) {
            throw new EmailExistenteException();
        }
    }

    private void validarAtualizacao(Long id, Usuario usuario) {

        var usuarioExistente = usuarioRepository.buscarPorEmail(usuario.getEmail());

        if (usuarioExistente != null && usuarioExistente.getId() != id) {
            throw new EmailExistenteException();
        }
    }

    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    public void remover(Long id) {
        usuarioRepository.remover(id);
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

        return usuarioRepository.atualizar(usuario);
    }

    public Usuario adicionarNovoCartao(Long id, Cartao cartao) {
        return usuarioRepository.adicionarNovoCartao(id, cartao);
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

    public List<Cartao> listarCartoesPorUsuario(Long id) {
        Usuario usuario = buscarExistentePorId(id);
        return usuario.getCartoes();
    }
}
