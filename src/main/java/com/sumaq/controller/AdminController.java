package com.sumaq.controller;

import com.sumaq.dto.ProductoFormDto;
import com.sumaq.service.ProductoService;
import com.sumaq.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private final ProductoService productoService;
    private final ReporteService reporteService;

    public AdminController(ProductoService productoService, ReporteService reporteService) {
        this.productoService = productoService;
        this.reporteService = reporteService;
    }

    @GetMapping("/admin")
    public String panel(Model model) {
        model.addAttribute("reporte", reporteService.obtenerResumen());
        model.addAttribute("productos", productoService.listarParaAdministracion());
        model.addAttribute("seccionActiva", "admin");
        return "admin/panel";
    }

    @GetMapping("/admin/productos/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("productoForm")) {
            model.addAttribute("productoForm", new ProductoFormDto());
        }
        prepararFormulario(model, null);
        return "admin/producto-form";
    }

    @PostMapping("/admin/productos")
    public String crear(
            @Valid @ModelAttribute("productoForm") ProductoFormDto formulario,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepararFormulario(model, null);
            return "admin/producto-form";
        }
        try {
            productoService.crear(formulario);
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("nombre", "producto.duplicado", exception.getMessage());
            prepararFormulario(model, null);
            return "admin/producto-form";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Producto creado correctamente");
        return "redirect:/admin";
    }

    @GetMapping("/admin/productos/{productoId}/editar")
    public String editar(@PathVariable Long productoId, Model model) {
        if (!model.containsAttribute("productoForm")) {
            model.addAttribute("productoForm", productoService.obtenerFormulario(productoId));
        }
        prepararFormulario(model, productoId);
        return "admin/producto-form";
    }

    @PostMapping("/admin/productos/{productoId}")
    public String actualizar(
            @PathVariable Long productoId,
            @Valid @ModelAttribute("productoForm") ProductoFormDto formulario,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepararFormulario(model, productoId);
            return "admin/producto-form";
        }
        try {
            productoService.actualizar(productoId, formulario);
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("nombre", "producto.duplicado", exception.getMessage());
            prepararFormulario(model, productoId);
            return "admin/producto-form";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado correctamente");
        return "redirect:/admin";
    }

    @PostMapping("/admin/productos/{productoId}/disponibilidad")
    public String cambiarDisponibilidad(
            @PathVariable Long productoId,
            @RequestParam boolean disponible,
            RedirectAttributes redirectAttributes) {
        productoService.cambiarDisponibilidad(productoId, disponible);
        redirectAttributes.addFlashAttribute(
                "mensaje",
                disponible ? "Producto habilitado en el menú" : "Producto retirado temporalmente del menú");
        return "redirect:/admin";
    }

    private void prepararFormulario(Model model, Long productoId) {
        model.addAttribute("categorias", productoService.listarCategoriasActivas());
        model.addAttribute("productoId", productoId);
        model.addAttribute("esEdicion", productoId != null);
        model.addAttribute("seccionActiva", "admin");
    }
}
