package com.siteoob.spacetrain.model.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostDAO {
    
    private final DataSource dataSource;

    @Autowired
    public PostDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void inserirPost(Post post) throws SQLException {
        String sql = "INSERT INTO posts (titulo, categoria, cliente_id) VALUES (?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, post.getTitulo());
            stmt.setString(2, post.getCategoria());
            stmt.setInt(3, post.getClienteId());
            
            stmt.executeUpdate();
        }
    }
}
