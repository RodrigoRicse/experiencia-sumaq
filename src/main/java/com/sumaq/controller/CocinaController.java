package com.sumaq.controller;

import com.sumaq.dto.PedidoCocinaDto;
import com.sumaq.service.PedidoConsultaService;
import com.sumaq.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CocinaController {

    private final PedidoConsultaService pedidoConsultaService;
    private final PedidoService pedidoService;

    public CocinaController(PedidoConsultaService pedidoConsultaService, PedidoService pedidoService) {
        this.pedidoConsultaService = pedidoConsultaService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/cocina")
    public String panel(Model model) {
        List<PedidoCocinaDto> pedidos = pedidoConsultaService.listarCocina();
        model.addAttribute("pendientes", filtrar(pedidos, "PENDIENTE"));
        model.addAttribute("enPreparacion", filtrar(pedidos, "EN_PREPARACION"));
        model.addAttribute("listos", filtrar(pedidos, "LISTO"));
        model.addAttribute("seccionActiva", "cocina");
        return "cocina/panel";
    }

    @PostMapping("/cocina/pedidos/{pedidoId}/iniciar")
    public String iniciar(@PathVariable Long pedidoId, RedirectAttributes redirectAttributes) {
        pedidoService.cambiarEstado(pedidoId, "EN_PREPARACION");
        redirectAttributes.addFlashAttribute("mensaje", "El pedido pasó a preparación");
        return "redirect:/cocina";
    }

    @PostMapping("/cocina/pedidos/{pedidoId}/listo")
    public String marcarListo(@PathVariable Long pedidoId, RedirectAttributes redirectAttributes) {
        pedidoService.cambiarEstado(pedidoId, "LISTO");
        redirectAttributes.addFlashAttribute("mensaje", "Pedido listo para recojo");
        return "redirect:/cocina";
    }

    private List<PedidoCocinaDto> filtrar(List<PedidoCocinaDto> pedidos, String estado) {
        return pedidos.stream().filter(pedido -> estado.equals(pedido.estado())).toList();
    }
}
