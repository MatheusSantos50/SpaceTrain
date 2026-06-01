
package com.siteoob.spacetrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.siteoob.spacetrain.model.cliente.Cliente;
import com.siteoob.spacetrain.model.cliente.ClienteService;
import com.siteoob.spacetrain.model.post.Post;
import com.siteoob.spacetrain.model.post.PostService;

@Controller
public class MainController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/")
    public String mainPage(){
        return "mainPage";
    }


    @GetMapping("/gallery")
    public String gallery(){
        return "gallery";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("cliente",new Cliente());
        return "register";
    }

    @PostMapping("/register")
    public String formCliente(@ModelAttribute Cliente cli, Model model){
        ClienteService cs = context.getBean(ClienteService.class);
		cs.inserirCliente(cli);
		return "sucesso";
    }

    @PostMapping("/post")
    public String adicionarPost(@ModelAttribute Post post, Model model){
        PostService ps = context.getBean(PostService.class);
        ps.inserirPost(post);
        return "sucesso";
    }
}
