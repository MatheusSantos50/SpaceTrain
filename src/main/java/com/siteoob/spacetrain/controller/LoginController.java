package com.siteoob.spacetrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.siteoob.spacetrain.model.usuario.Usuario;
import com.siteoob.spacetrain.model.usuario.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("nome") String nome,
                          @RequestParam("senha") String senha,
                          HttpSession session) {
        Usuario u = usuarioService.authenticate(nome, senha);
        if (u != null) {
            session.setAttribute("usuarioId", u.getId());
            session.setAttribute("usuarioNome", u.getNome());
            return "redirect:/";
        }
        return "redirect:/login?error=invalid";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
