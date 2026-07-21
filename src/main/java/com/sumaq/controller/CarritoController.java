package com.sumaq.controller;

import com.sumaq.exception.ProductoNoDisponibleException;
import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.service.CarritoService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping("/carrito/items")
    public String agregar(
            @RequestParam Long productoId,
            @RequestParam(required = false) Long categoria,
            RedirectAttributes redirectAttributes) {
        try {
            carritoService.agregar(productoId);
            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito");
        } catch (ProductoNoDisponibleException | RecursoNoEncontradoException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        if (categoria != null) {
            redirectAttributes.addAttribute("categoria", categoria);
        }
        return "redirect:/menu";
    }

    @PostMapping("/carrito/items/{productoId}/cantidad")
    public String actualizar(
            @PathVariable Long productoId,
            @RequestParam @Min(0) @Max(20) int cantidad,
            RedirectAttributes redirectAttributes) {
        carritoService.actualizar(productoId, cantidad);
        redirectAttributes.addFlashAttribute("mensaje", "Carrito actualizado");
        return "redirect:/menu";
    }

    @PostMapping("/carrito/items/{productoId}/eliminar")
    public String eliminar(@PathVariable Long productoId, RedirectAttributes redirectAttributes) {
        carritoService.eliminar(productoId);
        redirectAttributes.addFlashAttribute("mensaje", "Producto retirado del carrito");
        return "redirect:/menu";
    }
}
