package com.sumaq.controller;

import com.sumaq.dto.CarritoDto;
import com.sumaq.dto.CheckoutFormDto;
import com.sumaq.dto.PedidoRegistradoDto;
import com.sumaq.dto.RegistroPedidoDto;
import com.sumaq.model.EstadoPago;
import com.sumaq.model.MetodoPago;
import com.sumaq.service.CarritoService;
import com.sumaq.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PedidoWebController {

    private final CarritoService carritoService;
    private final PedidoService pedidoService;

    public PedidoWebController(CarritoService carritoService, PedidoService pedidoService) {
        this.carritoService = carritoService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/carrito")
    public String verCarrito() {
        return "redirect:/pedido/checkout";
    }

    @GetMapping("/pedido/checkout")
    public String checkout(Model model, RedirectAttributes redirectAttributes) {
        if (carritoService.estaVacio()) {
            redirectAttributes.addFlashAttribute("error", "Agrega al menos un producto antes de continuar");
            return "redirect:/menu";
        }
        if (!model.containsAttribute("checkoutForm")) {
            model.addAttribute("checkoutForm", new CheckoutFormDto());
        }
        prepararCheckout(model);
        return "cliente/checkout";
    }

    @PostMapping("/pedido/confirmar")
    public String confirmar(
            @Valid @ModelAttribute("checkoutForm") CheckoutFormDto formulario,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (carritoService.estaVacio()) {
            redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
            return "redirect:/menu";
        }
        if (bindingResult.hasErrors()) {
            prepararCheckout(model);
            return "cliente/checkout";
        }

        RegistroPedidoDto solicitud = carritoService.crearSolicitud(formulario);
        PedidoRegistradoDto pedido = pedidoService.registrar(solicitud);
        if (pedido.estadoPago() == EstadoPago.APROBADO) {
            carritoService.vaciar();
        }
        return "redirect:/pedido/confirmacion/" + pedido.codigoRecojo();
    }

    @GetMapping("/pedido/confirmacion/{codigoRecojo}")
    public String confirmacion(@PathVariable String codigoRecojo, Model model) {
        model.addAttribute("pedido", pedidoService.buscarResumenPorCodigo(codigoRecojo));
        model.addAttribute("paginaActiva", "pedido");
        return "cliente/confirmacion";
    }

    private void prepararCheckout(Model model) {
        CarritoDto carrito = carritoService.obtener();
        model.addAttribute("carrito", carrito);
        model.addAttribute("metodosPago", MetodoPago.values());
        model.addAttribute("paginaActiva", "pedido");
    }
}
