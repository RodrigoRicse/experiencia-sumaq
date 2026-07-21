package com.sumaq.dto;

import com.sumaq.model.EstadoPago;
import com.sumaq.model.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoCajaDto(
        Long id,
        String codigoRecojo,
        String cliente,
        String telefono,
        BigDecimal total,
        String estadoPedido,
        EstadoPago estadoPago,
        MetodoPago metodoPago,
        LocalDateTime creadoEn,
        List<DetalleOperacionDto> detalles) {

    public boolean puedeEntregarse() {
        return "LISTO".equals(estadoPedido) && estadoPago == EstadoPago.APROBADO;
    }
}
