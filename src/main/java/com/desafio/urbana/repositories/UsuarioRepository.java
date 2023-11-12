package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.exceptions.ConsultaUsuarioPorEmailException;
import com.desafio.urbana.exceptions.EmailExistenteExceptions;
import com.desafio.urbana.services.CartaoService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CartaoService cartaoService;

    public Usuario criarUsuario(Usuario usuario) {

        persistirUsuario(usuario);

        Long usuarioId = obterIdUsuarioPorEmail(usuario.getEmail());

        if (usuario.getCartao() != null) {
            cartaoService.associarCartoesAoUsuario(usuarioId, usuario.getCartao());
        }

        return usuario;
    }

    private void persistirUsuario(Usuario usuario) {

        String jpql = "INSERT INTO usuario(nome, email, senha) VALUES (:nome, :email, :senha)";
        Query usuarioQuery = entityManager.createNativeQuery(jpql)
                .setParameter("nome", usuario.getNome())
                .setParameter("email", usuario.getEmail())
                .setParameter("senha", usuario.getSenha());
        usuarioQuery.executeUpdate();

    }

    private Long obterIdUsuarioPorEmail(String email) {

        try {

            String jpql = "SELECT id FROM usuario WHERE email = :email";

            return ((Long) entityManager.createNativeQuery(jpql)
                    .setParameter("email", email)
                    .getSingleResult()).longValue();

        } catch (NonUniqueResultException e) {
            throw new EmailExistenteExceptions("E-mail já cadastrado no sistema.");
        }

    }

    public List<Usuario> listar() {

        String jpql = "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> usuarios = query.getResultList();

        return usuarios;
    }

    public Usuario buscar(String email) {

        try {

            String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";

            TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
            query.setParameter("email", email);
            Usuario usuarios = query.getSingleResult();

            return usuarios;

        } catch (NoResultException e) {
            throw new ConsultaUsuarioPorEmailException("Usuário não encontrado! Verifique o e-mail digitado.");
        }

    }

    public void removerUsuario(Long id) {

        Usuario usuario = entityManager.find(Usuario.class, id);

        List<Cartao> cartoes = usuario.getCartao();
        for (Cartao cartao : cartoes) {
            entityManager.createNativeQuery("DELETE FROM usuario_cartao WHERE cartao_id = :cartaoId")
                    .setParameter("cartaoId", cartao.getId())
                    .executeUpdate();
        }

        entityManager.remove(usuario);

    }

    public Usuario autalizarUsuario(Long id, Usuario usuario) {

        Usuario buscaUsuario = entityManager.find(Usuario.class, id);
        usuario.setId(id);

        if (buscaUsuario != null) {
            BeanUtils.copyProperties(usuario, buscaUsuario);
            entityManager.merge(buscaUsuario);
        }

        return buscaUsuario;

    }

    public Usuario adicionaNovoCartao(Long id, Cartao cartao) {

        List<Cartao> card = new ArrayList<>();
        card.add(cartao);

        Usuario usuario = entityManager.find(Usuario.class, id);
        cartaoService.associarCartoesAoUsuario(id, card);
        entityManager.merge(usuario);

        return usuario;

    }

}