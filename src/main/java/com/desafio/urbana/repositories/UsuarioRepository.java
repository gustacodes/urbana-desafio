package com.desafio.urbana.repositories;

import com.desafio.urbana.dto.UsuarioAtualizarDTO;
import com.desafio.urbana.entities.Cartao;
import com.desafio.urbana.entities.Usuario;
import com.desafio.urbana.exceptions.ConsultaUsuarioPorEmailException;
import com.desafio.urbana.exceptions.EmailExistenteExceptions;
import com.desafio.urbana.exceptions.UsuarioInexistenteExceptions;
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

        try {

            Usuario usuario = entityManager.find(Usuario.class, id);

            List<Cartao> cartoes = usuario.getCartao();

            for (Cartao cartao : cartoes) {
                entityManager.createNativeQuery("DELETE FROM usuario_cartao WHERE cartao_id = :cartaoId")
                        .setParameter("cartaoId", cartao.getId())
                        .executeUpdate();
            }

            entityManager.remove(usuario);

        } catch (NullPointerException exceptions) {
            throw new UsuarioInexistenteExceptions("Usuário não encontrado.");
        }

    }

    public Usuario autalizarUsuario(Long id, UsuarioAtualizarDTO usuarioDTO) {

        Usuario usuarioExistente = entityManager.find(Usuario.class, id);

        if (usuarioExistente != null) {
            if (usuarioDTO.nome() != null) {
                usuarioExistente.setNome(usuarioDTO.nome());
            }

            if (usuarioDTO.email() != null) {
                usuarioExistente.setEmail(usuarioDTO.email());
            }

            if (usuarioDTO.senha() != null) {
                usuarioExistente.setSenha(usuarioDTO.senha());
            }

            entityManager.merge(usuarioExistente);
        }

        return usuarioExistente;

    }

    public Usuario adicionaNovoCartao(Long id, Cartao cartao) {

        List<Cartao> card = new ArrayList<>();
        card.add(cartao);

        Usuario usuario = entityManager.find(Usuario.class, id);
        cartaoService.associarCartoesAoUsuario(id, card);
        entityManager.merge(usuario);

        return usuario;

    }

    public Usuario StatusCartao(Long id, Integer numero) {

        Usuario usuario = entityManager.find(Usuario.class, id);
        Boolean status;

        for (int i = 0; i < usuario.getCartao().size(); i++) {

            if (usuario.getCartao().get(i).getNumero_cartao() == numero) {
                if (usuario.getCartao().get(i).getStatus() == true) {
                    usuario.getCartao().get(i).setStatus(false);
                } else {
                    usuario.getCartao().get(i).setStatus(true);
                }
            }

        }

        entityManager.merge(usuario);

        return usuario;

    }

}