package com.sumaq.dto;

import java.util.List;

public record CatalogoDto(
        List<CategoriaCatalogoDto> categorias,
        CategoriaCatalogoDto categoriaSeleccionada,
        List<ProductoCatalogoDto> productos) {
}
