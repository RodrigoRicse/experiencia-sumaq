package com.sumaq.dto;

import java.math.BigDecimal;

public record ProductoCatalogoDto(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        String rutaImagen,
        String categoria,
        Integer calorias) {
}
