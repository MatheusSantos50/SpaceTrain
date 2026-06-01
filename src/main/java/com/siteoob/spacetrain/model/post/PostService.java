package com.siteoob.spacetrain.model.post;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    
    private final PostDAO postDAO;

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }
    
    public void inserirPost(Post post) {
        try {
            postDAO.inserirPost(post);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir post: " + e.getMessage());
        }
    }
}
