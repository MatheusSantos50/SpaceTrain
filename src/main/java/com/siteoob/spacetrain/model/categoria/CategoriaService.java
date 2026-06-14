package com.siteoob.spacetrain.model.categoria;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    @Autowired
    public CategoriaService(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public List<Categoria> listarCategorias() {
        try {
            return categoriaDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar categorias", e);
        }
    }
}
