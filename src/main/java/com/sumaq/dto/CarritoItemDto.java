package com.sumaq.dto;

import java.math.BigDecimal;

public record CarritoItemDto(
        Long productoId,
        String nombre,
        BigDecimal precioUnitario,
        String rutaImagen,
        int cantidad,
        BigDecimal subtotal) {
}
