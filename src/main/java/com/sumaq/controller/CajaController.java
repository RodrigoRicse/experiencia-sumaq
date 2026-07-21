package com.sumaq.controller;

import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.service.PedidoConsultaService;
import com.sumaq.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CajaController {

    private final PedidoConsultaService pedidoConsultaService;
    private final PedidoService pedidoService;

    public CajaController(PedidoConsultaService pedidoConsultaService, PedidoService pedidoService) {
        this.pedidoConsultaService = pedidoConsultaService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/caja")
    public String panel(
            @RequestParam(name = "codigo", required = false) String codigo,
            Model model) {
        model.addAttribute("codigoBuscado", codigo == null ? "" : codigo.trim());
        model.addAttribute("seccionActiva", "caja");
        if (codigo != null && !codigo.isBlank()) {
            try {
                model.addAttribute("pedido", pedidoConsultaService.buscarParaCaja(codigo));
            } catch (RecursoNoEncontradoException exception) {
                model.addAttribute("error", exception.getMessage());
            }
        }
        return "caja/panel";
    }

    @PostMapping("/caja/pedidos/{pedidoId}/entregar")
    public String entregar(
            @PathVariable Long pedidoId,
            @RequestParam String codigoRecojo,
            RedirectAttributes redirectAttributes) {
        pedidoService.cambiarEstado(pedidoId, "ENTREGADO");
        redirectAttributes.addFlashAttribute("mensaje", "Entrega confirmada correctamente");
        redirectAttributes.addAttribute("codigo", codigoRecojo);
        return "redirect:/caja";
    }
}
