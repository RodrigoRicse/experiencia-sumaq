package com.sumaq.dto;

import com.sumaq.model.EstadoPago;

import java.math.BigDecimal;

public record PedidoRegistradoDto(
        Long pedidoId,
        String codigoRecojo,
        BigDecimal total,
        String estadoPedido,
        EstadoPago estadoPago
) {
}
