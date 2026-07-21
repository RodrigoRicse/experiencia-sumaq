package com.sumaq.controller;

import com.sumaq.dto.CatalogoDto;
import com.sumaq.service.CatalogoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

    private final CatalogoService catalogoService;

    public ClienteController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("paginaActiva", "inicio");
        return "cliente/inicio";
    }

    @GetMapping("/menu")
    public String menu(
            @RequestParam(name = "categoria", required = false) Long categoriaId,
            Model model) {
        CatalogoDto catalogo = catalogoService.obtenerCatalogo(categoriaId);
        model.addAttribute("catalogo", catalogo);
        model.addAttribute("paginaActiva", "menu");
        return "cliente/menu";
    }
}
