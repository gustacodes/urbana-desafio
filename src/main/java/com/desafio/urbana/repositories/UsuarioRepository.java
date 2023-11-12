package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Usuario criarUsuario(Usuario usuario) {

        String usuarioJpql = "INSERT INTO usuario(nome, email, senha) VALUES (:nome, :email, :senha)";

        Query usuarioQuery = entityManager.createNativeQuery(usuarioJpql)
                .setParameter("nome", usuario.getNome())
                .setParameter("email", usuario.getEmail())
                .setParameter("senha", usuario.getSenha());

        usuarioQuery.executeUpdate();

        String jpql = "SELECT id FROM usuario WHERE email = :email";

        Long usuarioId = ((Long) entityManager.createNativeQuery(jpql)
                .setParameter("email", usuario.getEmail())
                .getSingleResult()).longValue();

        for (Cartao cartao : usuario.getCartao()) {

            String jpqlCartao = "INSERT INTO cartao(numero_cartao, nome, status, tipo_cartao, usuario_id) VALUES (:numero_cartao, :nome, :status, :tipo_cartao, :usuario_id)";

            entityManager.createNativeQuery(jpqlCartao)
                    .setParameter("numero_cartao", cartao.getNumero_cartao())
                    .setParameter("nome", cartao.getNome())
                    .setParameter("status", cartao.getStatus())
                    .setParameter("tipo_cartao", cartao.getTipoCartao().name())
                    .setParameter("usuario_id", usuarioId)
                    .executeUpdate();

            Long cartaoId = ((Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();

            String associacaoJpql = "INSERT INTO usuario_cartao(usuario_id, cartao_id) VALUES (:usuarioId, :cartaoId)";

            entityManager.createNativeQuery(associacaoJpql)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("cartaoId", cartaoId)
                    .executeUpdate();

        }

        return usuario;
    }

    public List<Usuario> listar() {

        String jpql = "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> usuarios = query.getResultList();

        return usuarios;
    }

    public Usuario buscar(String email) {

        String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";

        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        query.setParameter("email", email);
        Usuario usuarios = query.getSingleResult();
        System.out.println(usuarios.getNome());

        return usuarios;

    }

}
