package com.siteoob.spacetrain.model.categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaDAO {

    private final DataSource dataSource;

    @Autowired
    public CategoriaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Categoria> findAll() throws SQLException {
        String sql = "SELECT id, nome FROM categorias ORDER BY nome";
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("id"), rs.getString("nome"));
                categorias.add(categoria);
            }
        }

        return categorias;
    }
}
