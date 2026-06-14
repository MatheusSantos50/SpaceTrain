
package com.siteoob.spacetrain.model.imagem;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagemService {

    private final ImagemDAO imagemDAO;

    @Autowired
    public ImagemService(ImagemDAO imagemDAO) {
        this.imagemDAO = imagemDAO;
    }
    

    public void inserirImagem(Imagem imagem) {
        inserirImagem(imagem, null);
    }

    public void inserirImagem(Imagem imagem, List<Integer> categoriaIds) {
        try {
            int id = imagemDAO.inserirImagem(imagem);
            if (categoriaIds != null && !categoriaIds.isEmpty()) {
                imagemDAO.associarCategorias(id, categoriaIds);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir imagem", e);
        }
    }

    public List<Imagem> buscarImagens(List<Integer> categoriaIds) {
        try {
            return imagemDAO.buscarImagens(categoriaIds);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar imagens gerais", e);
        }
    }

    public List<Imagem> buscarImagensPorUsuario(int usuarioId) {
        return buscarImagensPorUsuario(usuarioId, null);
    }

    public List<Imagem> buscarImagensPorUsuario(int usuarioId, List<Integer> categoriaIds) {
        try {
            return imagemDAO.buscarImagensPorUsuario(usuarioId, categoriaIds);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar imagens do usuário", e);
        }
    }

    public Imagem buscarImagemPorId(int id) {
        try {
            return imagemDAO.buscarImagemPorId(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar imagem por ID", e);
        }
    }

    public void atualizarImagem(Imagem imagem, List<Integer> categoriaIds) {
        try {
            imagemDAO.atualizarImagem(imagem);
            imagemDAO.deletarCategoriasDaImagem(imagem.getId());
            if (categoriaIds != null && !categoriaIds.isEmpty()) {
                imagemDAO.associarCategorias(imagem.getId(), categoriaIds);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar imagem", e);
        }
    }

    public void deletarImagem(int id) {
        try {
            Imagem img = imagemDAO.buscarImagemPorId(id);
            if (img != null) {
                imagemDAO.deletarImagem(id);
                // Tenta excluir o arquivo físico do disco
                String uploadsDir = System.getProperty("user.dir") + img.getEndereco();
                java.io.File file = new java.io.File(uploadsDir);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar imagem", e);
        }
    }

}
