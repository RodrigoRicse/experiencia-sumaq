package com.sumaq.controller;

import com.sumaq.service.CarritoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CarritoModelAdvice {

    private final CarritoService carritoService;

    public CarritoModelAdvice(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @ModelAttribute("cantidadCarrito")
    public int cantidadCarrito() {
        return carritoService.cantidadTotal();
    }
}
