package com.siteoob.spacetrain.model.imagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImagemDAO {

    private final DataSource dataSource;

    @Autowired
    public ImagemDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int inserirImagem(Imagem imagem) throws SQLException {
        String sql = "INSERT INTO imagens (usuario_id, titulo, descricao, endereco) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, imagem.getUsuarioId());
            stmt.setString(2, imagem.getTitulo());
            stmt.setString(3, imagem.getDescricao());
            stmt.setString(4, imagem.getEndereco());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Falha ao obter o ID gerado para a imagem.");
    }

    public void associarCategorias(int imagemId, List<Integer> categoriaIds) throws SQLException {
        if (categoriaIds == null || categoriaIds.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO imagem_categorias (imagem_id, categoria_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int catId : categoriaIds) {
                stmt.setInt(1, imagemId);
                stmt.setInt(2, catId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public List<Imagem> buscarImagens(List<Integer> categoriaIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        if (categoriaIds != null && !categoriaIds.isEmpty()) {
            sql.append("SELECT DISTINCT i.id, i.usuario_id, i.titulo, i.descricao, i.endereco, i.data_criacao, i.visualizacoes, i.curtidas, i.ativo ");
            sql.append("FROM imagens i ");
            sql.append("JOIN imagem_categorias ic ON i.id = ic.imagem_id ");
            sql.append("WHERE i.ativo = TRUE AND ic.categoria_id IN (");
            for (int i = 0; i < categoriaIds.size(); i++) {
                sql.append("?");
                if (i < categoriaIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") ORDER BY i.data_criacao DESC");
        } else {
            sql.append("SELECT id, usuario_id, titulo, descricao, endereco, data_criacao, visualizacoes, curtidas, ativo ");
            sql.append("FROM imagens WHERE ativo = TRUE ORDER BY data_criacao DESC");
        }

        List<Imagem> imagens = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            if (categoriaIds != null && !categoriaIds.isEmpty()) {
                for (int i = 0; i < categoriaIds.size(); i++) {
                    stmt.setInt(i + 1, categoriaIds.get(i));
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Imagem imagem = new Imagem();
                    imagem.setId(rs.getInt("id"));
                    imagem.setUsuarioId(rs.getInt("usuario_id"));
                    imagem.setTitulo(rs.getString("titulo"));
                    imagem.setDescricao(rs.getString("descricao"));
                    imagem.setEndereco(rs.getString("endereco"));
                    Timestamp ts = rs.getTimestamp("data_criacao");
                    if (ts != null) {
                        imagem.setDataCriacao(ts.toLocalDateTime());
                    }
                    imagem.setVisualizacoes(rs.getInt("visualizacoes"));
                    imagem.setCurtidas(rs.getInt("curtidas"));
                    imagem.setAtivo(rs.getBoolean("ativo"));
                    imagem.setCategoriaIds(buscarCategoriasDaImagem(imagem.getId()));
                    imagens.add(imagem);
                }
            }
        }
        return imagens;
    }

    public List<Imagem> buscarImagensPorUsuario(int usuarioId) throws SQLException {
        return buscarImagensPorUsuario(usuarioId, null);
    }

    public List<Imagem> buscarImagensPorUsuario(int usuarioId, List<Integer> categoriaIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        if (categoriaIds != null && !categoriaIds.isEmpty()) {
            sql.append("SELECT DISTINCT i.id, i.usuario_id, i.titulo, i.descricao, i.endereco, i.data_criacao, i.visualizacoes, i.curtidas, i.ativo ");
            sql.append("FROM imagens i ");
            sql.append("JOIN imagem_categorias ic ON i.id = ic.imagem_id ");
            sql.append("WHERE i.usuario_id = ? AND i.ativo = TRUE AND ic.categoria_id IN (");
            for (int i = 0; i < categoriaIds.size(); i++) {
                sql.append("?");
                if (i < categoriaIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") ORDER BY i.data_criacao DESC");
        } else {
            sql.append("SELECT id, usuario_id, titulo, descricao, endereco, data_criacao, visualizacoes, curtidas, ativo ");
            sql.append("FROM imagens WHERE usuario_id = ? AND ativo = TRUE ORDER BY data_criacao DESC");
        }

        List<Imagem> imagens = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            stmt.setInt(1, usuarioId);
            if (categoriaIds != null && !categoriaIds.isEmpty()) {
                for (int i = 0; i < categoriaIds.size(); i++) {
                    stmt.setInt(i + 2, categoriaIds.get(i));
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Imagem imagem = new Imagem();
                    imagem.setId(rs.getInt("id"));
                    imagem.setUsuarioId(rs.getInt("usuario_id"));
                    imagem.setTitulo(rs.getString("titulo"));
                    imagem.setDescricao(rs.getString("descricao"));
                    imagem.setEndereco(rs.getString("endereco"));
                    Timestamp ts = rs.getTimestamp("data_criacao");
                    if (ts != null) {
                        imagem.setDataCriacao(ts.toLocalDateTime());
                    }
                    imagem.setVisualizacoes(rs.getInt("visualizacoes"));
                    imagem.setCurtidas(rs.getInt("curtidas"));
                    imagem.setAtivo(rs.getBoolean("ativo"));
                    imagem.setCategoriaIds(buscarCategoriasDaImagem(imagem.getId()));
                    imagens.add(imagem);
                }
            }
        }
        return imagens;
    }

    public List<Integer> buscarCategoriasDaImagem(int imagemId) throws SQLException {
        String sql = "SELECT categoria_id FROM imagem_categorias WHERE imagem_id = ?";
        List<Integer> ids = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, imagemId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("categoria_id"));
                }
            }
        }
        return ids;
    }

    public Imagem buscarImagemPorId(int id) throws SQLException {
        String sql = "SELECT id, usuario_id, titulo, descricao, endereco, data_criacao, visualizacoes, curtidas, ativo FROM imagens WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Imagem imagem = new Imagem();
                    imagem.setId(rs.getInt("id"));
                    imagem.setUsuarioId(rs.getInt("usuario_id"));
                    imagem.setTitulo(rs.getString("titulo"));
                    imagem.setDescricao(rs.getString("descricao"));
                    imagem.setEndereco(rs.getString("endereco"));
                    Timestamp ts = rs.getTimestamp("data_criacao");
                    if (ts != null) {
                        imagem.setDataCriacao(ts.toLocalDateTime());
                    }
                    imagem.setVisualizacoes(rs.getInt("visualizacoes"));
                    imagem.setCurtidas(rs.getInt("curtidas"));
                    imagem.setAtivo(rs.getBoolean("ativo"));
                    imagem.setCategoriaIds(buscarCategoriasDaImagem(imagem.getId()));
                    return imagem;
                }
            }
        }
        return null;
    }

    public void atualizarImagem(Imagem imagem) throws SQLException {
        String sql = "UPDATE imagens SET titulo = ?, descricao = ?, endereco = ? WHERE id = ? AND usuario_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, imagem.getTitulo());
            stmt.setString(2, imagem.getDescricao());
            stmt.setString(3, imagem.getEndereco());
            stmt.setInt(4, imagem.getId());
            stmt.setInt(5, imagem.getUsuarioId());
            stmt.executeUpdate();
        }
    }

    public void deletarCategoriasDaImagem(int imagemId) throws SQLException {
        String sql = "DELETE FROM imagem_categorias WHERE imagem_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, imagemId);
            stmt.executeUpdate();
        }
    }

    public void deletarImagem(int id) throws SQLException {
        String sql = "DELETE FROM imagens WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
