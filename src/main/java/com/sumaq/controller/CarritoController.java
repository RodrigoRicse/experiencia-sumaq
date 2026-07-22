package com.sumaq.controller;

import com.sumaq.dto.CarritoDto;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
            @RequestParam(required = false) String origen,
            RedirectAttributes redirectAttributes) {
        carritoService.actualizar(productoId, cantidad);
        redirectAttributes.addFlashAttribute("mensaje", "Carrito actualizado");
        return redireccion(origen);
    }

    @PostMapping("/carrito/items/{productoId}/eliminar")
    public String eliminar(
            @PathVariable Long productoId,
            @RequestParam(required = false) String origen,
            RedirectAttributes redirectAttributes) {
        carritoService.eliminar(productoId);
        redirectAttributes.addFlashAttribute("mensaje", "Producto retirado del carrito");
        return redireccion(origen);
    }

    @PostMapping(value = "/carrito/items/{productoId}/cantidad", headers = "X-Requested-With=XMLHttpRequest")
    @ResponseBody
    public CarritoDto actualizarDesdeCheckout(
            @PathVariable Long productoId,
            @RequestParam @Min(0) @Max(20) int cantidad) {
        carritoService.actualizar(productoId, cantidad);
        return carritoService.obtener();
    }

    @PostMapping(value = "/carrito/items/{productoId}/eliminar", headers = "X-Requested-With=XMLHttpRequest")
    @ResponseBody
    public CarritoDto eliminarDesdeCheckout(@PathVariable Long productoId) {
        carritoService.eliminar(productoId);
        return carritoService.obtener();
    }

    private String redireccion(String origen) {
        return "checkout".equals(origen) ? "redirect:/pedido/checkout" : "redirect:/menu";
    }
}
