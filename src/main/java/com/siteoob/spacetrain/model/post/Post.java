package com.siteoob.spacetrain.model.post;

import java.time.LocalDateTime;

public class Post {
    
    private int id;
    private String titulo;
    private String categoria;
    private int clienteId;
    private LocalDateTime dataCriacao;
    
    public Post() {}
    
    public Post(String titulo, String categoria, int clienteId) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.clienteId = clienteId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
