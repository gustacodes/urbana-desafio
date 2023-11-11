package com.desafio.urbana.repositories;

import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Usuario criarUsuario(Usuario usuario) {

        String usuarioJpql = "INSERT INTO Usuario(nome, email, senha) VALUES (:nome, :email, :senha)";
        String associacaoJpql = "INSERT INTO Usuario_Cartao(usuario_id, cartao_id) VALUES (:usuarioId, :cartaoId)";
        String jpqlCartao = "INSERT INTO Cartao(numeroCartao, nome, status, tipoCartao) VALUES (:numeroCartao, :nome, :status, :tipoCartao)";

        // Executar a consulta de criação de usuário
        Query usuarioQuery = entityManager.createNativeQuery(usuarioJpql)
                .setParameter("nome", usuario.getNome())
                .setParameter("email", usuario.getEmail())
                .setParameter("senha", usuario.getSenha());

        // Executar a consulta para criar o usuário
        usuarioQuery.executeUpdate();

        // Obter o ID do usuário recém-criado
        Long usuarioId = ((Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();

        // Associar o usuário aos cartões na tabela de junção
        for (Cartao cartao : usuario.getCartao()) {

            entityManager.createNativeQuery(jpqlCartao)
                    .setParameter("numeroCartao", cartao.getNumeroCartao())
                    .setParameter("nome", cartao.getNome())
                    .setParameter("status", cartao.getStatus())
                    .setParameter("tipoCartao", cartao.getTipoCartao().name())
                    .executeUpdate();

            Long cartaoId = ((Long) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();

            entityManager.createNativeQuery(associacaoJpql)
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("cartaoId", cartaoId)
                    .executeUpdate();

        }

        return usuario;
    }

}
