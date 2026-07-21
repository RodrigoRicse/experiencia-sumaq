package com.sumaq.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoCocinaDto(
        Long id,
        String codigoRecojo,
        String estado,
        LocalDateTime creadoEn,
        long minutosEspera,
        String observaciones,
        List<DetalleOperacionDto> detalles) {
}
