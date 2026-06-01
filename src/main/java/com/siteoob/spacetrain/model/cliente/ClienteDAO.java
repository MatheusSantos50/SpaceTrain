package com.siteoob.spacetrain.model.cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {
    
    private static String url = "jdbc:postgresql://localhost:5432/spacetrain";
    private static String user = "postgres";
    private static String password = "sua_senha_aqui";
    
    public static void inserirCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            
            stmt.executeUpdate();
        }
    }
}
