package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.exceptions.ConsultaUsuarioPorEmailException;
import com.desafio.urbana.exceptions.UsuarioInexistenteException;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Usuario buscarPorId(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Transactional
    public Usuario persistirUsuario(Usuario usuario) {
        String insert = "INSERT INTO usuario(nome, email, senha) VALUES (:nome, :email, :senha)";

        Query usuarioQuery = entityManager.createNativeQuery(insert)
                .setParameter("nome", usuario.getNome())
                .setParameter("email", usuario.getEmail())
                .setParameter("senha", usuario.getSenha());
        usuarioQuery.executeUpdate();

        return usuario;
    }

    public List<Usuario> listar() {
        String select = "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = entityManager.createQuery(select, Usuario.class);
        List<Usuario> usuarios = query.getResultList();

        return usuarios;
    }

    public Usuario buscarPorEmail(String email) {
        String select = "SELECT u FROM Usuario u WHERE u.email = :email";

        TypedQuery<Usuario> query = entityManager.createQuery(select, Usuario.class);
        query.setParameter("email", email);
        List<Usuario> resultado = query.getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Transactional
    public void remover(Long id) {
        entityManager.createNativeQuery("DELETE FROM usuario_cartao WHERE usuario_id = :usuario_id")
                .setParameter("usuario_id", id)
                .executeUpdate();

        entityManager.remove(buscarPorId(id));
    }

    @Transactional
    public Usuario atualizar(Usuario usuario) {
        return entityManager.merge(usuario);
    }

    @Transactional
    public Usuario adicionarNovoCartao(Long id, Cartao cartao) {
        List<Cartao> card = new ArrayList<>();
        card.add(cartao);

        Usuario usuario = entityManager.find(Usuario.class, id);
        cartaoRepository.associarCartoesAoUsuario(id, card);
        entityManager.merge(usuario);

        return usuario;
    }
}