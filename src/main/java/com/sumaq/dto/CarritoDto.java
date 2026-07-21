package com.sumaq.dto;

import java.math.BigDecimal;
import java.util.List;

public record CarritoDto(List<CarritoItemDto> items, int cantidadTotal, BigDecimal total) {

    public boolean estaVacio() {
        return items.isEmpty();
    }
}
