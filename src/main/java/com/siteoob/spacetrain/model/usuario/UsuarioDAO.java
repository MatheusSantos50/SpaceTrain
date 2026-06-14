package com.siteoob.spacetrain.model.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO {

    private final DataSource dataSource;

    @Autowired
    public UsuarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Usuario findByNomeAndSenha(String nome, String senha) throws SQLException {
        String sql = "SELECT id, nome, email, senha FROM usuarios WHERE nome = ? AND senha = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    return u;
                }
            }
        }
        return null;
    }
}
