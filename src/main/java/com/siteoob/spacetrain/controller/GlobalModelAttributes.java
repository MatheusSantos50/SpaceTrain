package com.siteoob.spacetrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.siteoob.spacetrain.model.categoria.Categoria;
import com.siteoob.spacetrain.model.categoria.CategoriaService;

@ControllerAdvice
public class GlobalModelAttributes {

    private final CategoriaService categoriaService;

    @Autowired
    public GlobalModelAttributes(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @ModelAttribute("categorias")
    public List<Categoria> categorias() {
        return categoriaService.listarCategorias();
    }
}
