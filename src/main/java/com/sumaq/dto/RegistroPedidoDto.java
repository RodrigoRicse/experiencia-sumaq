package com.sumaq.dto;

import com.sumaq.model.MetodoPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegistroPedidoDto(
        @NotNull @Valid ClientePedidoDto cliente,
        @NotEmpty List<@Valid ItemPedidoDto> items,
        @Size(max = 500) String observaciones,
        @NotNull MetodoPago metodoPago,
        @NotNull Boolean pagoAprobado
) {
}
