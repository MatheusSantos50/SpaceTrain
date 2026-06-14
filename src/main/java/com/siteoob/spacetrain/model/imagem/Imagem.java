package com.siteoob.spacetrain.model.imagem;

import java.time.LocalDateTime;
import java.util.List;

public class Imagem {

    private int id;
    private int usuarioId;
    private int visualizacoes;
    private int curtidas;
    private LocalDateTime dataCriacao;
    private String titulo;
    private String descricao;
    private String endereco;
    private List<Integer> categoriaIds;

    private boolean ativo;

    public Imagem(){}

    public Imagem(boolean ativo, int curtidas, LocalDateTime dataCriacao, String descricao, String endereco, int id, String titulo, int usuarioId, int visualizacoes) {
        this.ativo = ativo;
        this.curtidas = curtidas;
        this.dataCriacao = dataCriacao;
        this.descricao = descricao;
        this.endereco = endereco;
        this.id = id;
        this.titulo = titulo;
        this.usuarioId = usuarioId;
        this.visualizacoes = visualizacoes;
    }

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Integer> getCategoriaIds() {
        return categoriaIds;
    }

    public void setCategoriaIds(List<Integer> categoriaIds) {
        this.categoriaIds = categoriaIds;
    }
}