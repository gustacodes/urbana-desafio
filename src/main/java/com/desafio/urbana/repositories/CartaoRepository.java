package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class CartaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

        cartao.setNumero_cartao(numero);

        String jpql = "INSERT INTO cartao(numero_cartao, nome, status, tipo_cartao, usuario_id) VALUES (:numero_cartao, :nome, :status, :tipo_cartao, :usuario_id)";
        entityManager.createNativeQuery(jpql)
                .setParameter("numero_cartao", cartao.getNumero_cartao())
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

    public void removerCartao(Long id) {

        Cartao cartao = entityManager.find(Cartao.class, id);

        entityManager.createNativeQuery("DELETE FROM usuario_cartao WHERE cartao_id = :cartaoId")
                .setParameter("cartaoId", cartao.getId())
                .executeUpdate();

        entityManager.remove(cartao);
    }

}
