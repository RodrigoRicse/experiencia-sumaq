package com.sumaq.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReporteAdminDto(
        long pedidosTotales,
        long pedidosAprobados,
        BigDecimal ingresosAprobados,
        BigDecimal ticketPromedio,
        long productosDisponibles,
        long productosNoDisponibles,
        List<VentaCategoriaDto> ventasPorCategoria) {
}
