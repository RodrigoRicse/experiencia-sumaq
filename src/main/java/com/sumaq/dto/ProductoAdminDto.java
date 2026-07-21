package com.sumaq.dto;

import java.math.BigDecimal;

public record ProductoAdminDto(
        Long id,
        String categoria,
        String nombre,
        String descripcion,
        BigDecimal precio,
        String rutaImagen,
        boolean disponible,
        Integer calorias) {
}
