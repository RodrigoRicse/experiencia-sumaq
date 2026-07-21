package com.sumaq.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ItemPedidoDto(
        @NotNull Long productoId,
        @Min(1) int cantidad,
        @Size(max = 300) String observaciones
) {
}
