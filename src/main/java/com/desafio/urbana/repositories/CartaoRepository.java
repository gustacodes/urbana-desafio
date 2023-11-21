package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class CartaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cartao> listarCartoes() {
        String select = "SELECT u FROM Cartao u";
        TypedQuery<Cartao> query = entityManager.createQuery(select, Cartao.class);
        List<Cartao> cartoes = query.getResultList();

        return cartoes;
    }

    @Transactional
    public Cartao alterarStatus(Long id) {
        Cartao cartao = buscarCartaoId(id);
        cartao.setStatus(!cartao.getStatus());
        entityManager.merge(cartao);

        return cartao;
    }

    public Cartao buscarCartaoId(Long id) {
        return entityManager.find(Cartao.class, id);
    }

    public void associarCartoesAoUsuario(Long usuarioId, List<Cartao> cartoes) {
        for (Cartao cartao : cartoes) {
            persistirCartao(cartao, usuarioId);
            Long cartaoId = obterUltimoIdInserido();
            associarUsuarioCartao(usuarioId, cartaoId);
        }
    }

    public void persistirCartao(Cartao cartao, Long usuarioId) {
        var numeroGerado = new Random();
        int numero = numeroGerado.nextInt(1000000);

        cartao.setNumeroCartao(numero);

        String jpql = "INSERT INTO cartao(numero_cartao, nome, status, tipo_cartao, usuario_id) VALUES (:numero_cartao, :nome, :status, :tipo_cartao, :usuario_id)";
        entityManager.createNativeQuery(jpql)
                .setParameter("numero_cartao", cartao.getNumeroCartao())
                .setParameter("nome", cartao.getNome())
                .setParameter("status", true)
                .setParameter("tipo_cartao", cartao.getTipoCartao().name())
                .setParameter("usuario_id", usuarioId)
                .executeUpdate();
    }

    public Long obterUltimoIdInserido() {
        return ((Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();
    }

    public void associarUsuarioCartao(Long usuarioId, Long cartaoId) {
        String jpql = "INSERT INTO usuario_cartao(usuario_id, cartao_id) VALUES (:usuarioId, :cartaoId)";
        entityManager.createNativeQuery(jpql)
                .setParameter("usuarioId", usuarioId)
                .setParameter("cartaoId", cartaoId)
                .executeUpdate();
    }

    public void removerCartao(Long idCartao) {
        Cartao cartao = entityManager.find(Cartao.class, idCartao);

        entityManager.createNativeQuery("DELETE FROM usuario_cartao WHERE cartao_id = :cartaoId")
                .setParameter("cartaoId", cartao.getId())
                .executeUpdate();

        entityManager.remove(cartao);
    }

}