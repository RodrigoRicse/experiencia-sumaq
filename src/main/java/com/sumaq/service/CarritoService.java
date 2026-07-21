package com.sumaq.service;

import com.sumaq.dto.CarritoDto;
import com.sumaq.dto.CarritoItemDto;
import com.sumaq.dto.CheckoutFormDto;
import com.sumaq.dto.ClientePedidoDto;
import com.sumaq.dto.ItemPedidoDto;
import com.sumaq.dto.RegistroPedidoDto;
import com.sumaq.model.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {

    private final CarritoSesion carritoSesion;
    private final ProductoService productoService;

    public CarritoService(CarritoSesion carritoSesion, ProductoService productoService) {
        this.carritoSesion = carritoSesion;
        this.productoService = productoService;
    }

    @Transactional(readOnly = true)
    public void agregar(Long productoId) {
        productoService.obtenerDisponible(productoId);
        carritoSesion.agregar(productoId);
    }

    @Transactional(readOnly = true)
    public void actualizar(Long productoId, int cantidad) {
        if (cantidad > 0) {
            productoService.obtenerDisponible(productoId);
        }
        carritoSesion.actualizar(productoId, cantidad);
    }

    public void eliminar(Long productoId) {
        carritoSesion.eliminar(productoId);
    }

    @Transactional(readOnly = true)
    public CarritoDto obtener() {
        List<CarritoItemDto> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var entrada : carritoSesion.obtenerCantidades().entrySet()) {
            Producto producto = productoService.obtenerDisponible(entrada.getKey());
            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(entrada.getValue()));
            items.add(new CarritoItemDto(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getRutaImagen(),
                    entrada.getValue(),
                    subtotal));
            total = total.add(subtotal);
        }

        return new CarritoDto(List.copyOf(items), carritoSesion.cantidadTotal(), total.setScale(2));
    }

    @Transactional(readOnly = true)
    public RegistroPedidoDto crearSolicitud(CheckoutFormDto formulario) {
        CarritoDto carrito = obtener();
        List<ItemPedidoDto> items = carrito.items().stream()
                .map(item -> new ItemPedidoDto(item.productoId(), item.cantidad(), null))
                .toList();
        ClientePedidoDto cliente = new ClientePedidoDto(
                formulario.getNombres(),
                formulario.getApellidos(),
                formulario.getTelefono(),
                formulario.getEmail());
        return new RegistroPedidoDto(
                cliente,
                items,
                formulario.getObservaciones(),
                formulario.getMetodoPago(),
                formulario.getPagoAprobado());
    }

    public int cantidadTotal() {
        return carritoSesion.cantidadTotal();
    }

    public boolean estaVacio() {
        return carritoSesion.estaVacio();
    }

    public void vaciar() {
        carritoSesion.vaciar();
    }
}
