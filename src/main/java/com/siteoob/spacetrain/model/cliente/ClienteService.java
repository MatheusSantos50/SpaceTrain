package com.siteoob.spacetrain.model.cliente;

import java.sql.SQLException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    public void inserirCliente(Cliente cliente) {
        try {
            ClienteDAO.inserirCliente(cliente);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir cliente: " + e.getMessage());
        }
    }
}
