package com.siteoob.spacetrain.model.usuario;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario authenticate(String nome, String senha) {
        try {
            Usuario u = usuarioDAO.findByNome(nome);
            if (u != null && BCrypt.checkpw(senha, u.getSenha())) {
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(Usuario u) {
        try {
            if (usuarioDAO.existsByEmail(u.getEmail())) {
                throw new IllegalArgumentException("email");
            }
            if (usuarioDAO.existsByNome(u.getNome())) {
                throw new IllegalArgumentException("nome");
            }
            String hashedPassword = BCrypt.hashpw(u.getSenha(), BCrypt.gensalt());
            u.setSenha(hashedPassword);
            usuarioDAO.save(u);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
