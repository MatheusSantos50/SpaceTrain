
package com.siteoob.spacetrain.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.siteoob.spacetrain.model.imagem.Imagem;
import com.siteoob.spacetrain.model.imagem.ImagemService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/")
    public String mainPage(@RequestParam(value = "categoriaIds", required = false) List<Integer> categoriaIds, Model model){
        ImagemService is = context.getBean(ImagemService.class);
        model.addAttribute("imagens", is.buscarImagens(categoriaIds));
        model.addAttribute("currentPage", "explore");
        return "mainPage";
    }


    @GetMapping("/gallery")
    public String gallery(@RequestParam(value = "categoriaIds", required = false) List<Integer> categoriaIds, Model model, HttpSession session){
        Object uid = session.getAttribute("usuarioId");
        if (uid == null) {
            return "redirect:/login";
        }

        ImagemService is = context.getBean(ImagemService.class);
        model.addAttribute("imagensUsuario", is.buscarImagensPorUsuario((Integer) uid, categoriaIds));
        model.addAttribute("currentPage", "gallery");
        return "gallery";
    }


    @PostMapping("/imagem")
    public String adicionarImagem(@RequestParam("file") MultipartFile file,
                                  @RequestParam("titulo") String titulo,
                                  @RequestParam(value = "descricao", required = false) String descricao,
                                  @RequestParam(value = "categoriaIds", required = false) List<Integer> categoriaIds,
                                  Model model,
                                  HttpSession session) {
        System.out.println("Estou aqui");
        // Save uploaded file to root uploads folder
        String uploadsDir = System.getProperty("user.dir") + File.separator + "uploads";
        try {
            File uploadFolder = new File(uploadsDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String filename = System.currentTimeMillis() + "_" + originalFilename;
            Path destination = Paths.get(uploadsDir).resolve(filename);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String endereco = "/uploads/" + filename; // URL path used in templates

            Imagem imagem = new Imagem();
            imagem.setTitulo(titulo);
            imagem.setDescricao(descricao);
            imagem.setEndereco(endereco);
            Object uid = session.getAttribute("usuarioId");
            if (uid == null) {
                // not logged in
                return "redirect:/login";
            }
            imagem.setUsuarioId((Integer) uid);

            ImagemService is = context.getBean(ImagemService.class);
            is.inserirImagem(imagem, categoriaIds);

            return "redirect:/gallery";
        } catch (IOException e) {
            model.addAttribute("error", "Erro ao salvar arquivo: " + e.getMessage());
            return "redirect:/gallery?error=upload"; // redirect back with error flag
        }
    }

    @PostMapping("/imagem/editar")
    public String editarImagem(@RequestParam("id") int id,
                               @RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestParam("titulo") String titulo,
                               @RequestParam(value = "descricao", required = false) String descricao,
                               @RequestParam(value = "categoriaIds", required = false) List<Integer> categoriaIds,
                               HttpSession session,
                               Model model) {
        Object uid = session.getAttribute("usuarioId");
        if (uid == null) {
            return "redirect:/login";
        }

        ImagemService is = context.getBean(ImagemService.class);
        Imagem imagem = is.buscarImagemPorId(id);
        if (imagem == null || imagem.getUsuarioId() != (Integer) uid) {
            return "redirect:/gallery?error=unauthorized";
        }

        imagem.setTitulo(titulo);
        imagem.setDescricao(descricao);

        if (file != null && !file.isEmpty()) {
            String uploadsDir = System.getProperty("user.dir") + File.separator + "uploads";
            try {
                // Remove o arquivo físico anterior se ele existir
                if (imagem.getEndereco() != null) {
                    File oldFile = new File(System.getProperty("user.dir") + imagem.getEndereco());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // Salva o novo arquivo
                String originalFilename = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
                String filename = System.currentTimeMillis() + "_" + originalFilename;
                Path destination = Paths.get(uploadsDir).resolve(filename);
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

                imagem.setEndereco("/uploads/" + filename);
            } catch (IOException e) {
                return "redirect:/gallery?error=edit-upload";
            }
        }

        is.atualizarImagem(imagem, categoriaIds);
        return "redirect:/gallery";
    }

    @PostMapping("/imagem/deletar")
    public String deletarImagem(@RequestParam("id") int id, HttpSession session) {
        Object uid = session.getAttribute("usuarioId");
        if (uid == null) {
            return "redirect:/login";
        }

        ImagemService is = context.getBean(ImagemService.class);
        Imagem imagem = is.buscarImagemPorId(id);
        if (imagem != null && imagem.getUsuarioId() == (Integer) uid) {
            is.deletarImagem(id);
        }
        return "redirect:/gallery";
    }
}
