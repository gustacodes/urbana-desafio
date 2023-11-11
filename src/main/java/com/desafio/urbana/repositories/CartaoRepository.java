package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CartaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void criarCartao(Usuario usuario) {
        // Associar o usuário ao cartão na tabela de junção
        String cartaoAssociacaoJpql = "INSERT INTO Usuario_Cartao(usuario_id, cartao_id) VALUES (:usuarioId, :cartaoId)";
        entityManager.createNativeQuery(cartaoAssociacaoJpql)
                .setParameter("usuarioId", usuario.getId())
                .setParameter("cartaoId", usuario.getId())  // Substitua idDoCartao pelo ID do cartão desejado
                .executeUpdate();
    }
}
