package com.siteoob.spacetrain.model.usuario;

import java.sql.SQLException;

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
            return usuarioDAO.findByNomeAndSenha(nome, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
