package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.execptions.ConsultaUsuarioPorEmailException;
import com.desafio.urbana.execptions.EmailExistenteExceptions;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Usuario criarUsuario(Usuario usuario) {

        persistirUsuario(usuario);

        Long usuarioId = obterIdUsuarioPorEmail(usuario.getEmail());

        if (usuario.getCartao() != null) {
            associarCartoesAoUsuario(usuarioId, usuario.getCartao());
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

    private void associarCartoesAoUsuario(Long usuarioId, List<Cartao> cartoes) {
        for (Cartao cartao : cartoes) {
            persistirCartao(cartao, usuarioId);
            Long cartaoId = obterUltimoIdInserido();
            associarUsuarioCartao(usuarioId, cartaoId);
        }
    }

    private void persistirCartao(Cartao cartao, Long usuarioId) {
        String jpql = "INSERT INTO cartao(numero_cartao, nome, status, tipo_cartao, usuario_id) VALUES (:numero_cartao, :nome, :status, :tipo_cartao, :usuario_id)";
        entityManager.createNativeQuery(jpql)
                .setParameter("numero_cartao", cartao.getNumero_cartao())
                .setParameter("nome", cartao.getNome())
                .setParameter("status", true)
                .setParameter("tipo_cartao", cartao.getTipoCartao().name())
                .setParameter("usuario_id", usuarioId)
                .executeUpdate();
    }

    private Long obterUltimoIdInserido() {
        return ((Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();
    }

    private void associarUsuarioCartao(Long usuarioId, Long cartaoId) {
        String jpql = "INSERT INTO usuario_cartao(usuario_id, cartao_id) VALUES (:usuarioId, :cartaoId)";
        entityManager.createNativeQuery(jpql)
                .setParameter("usuarioId", usuarioId)
                .setParameter("cartaoId", cartaoId)
                .executeUpdate();
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

}