package com.sumaq.service;

import com.sumaq.dto.CatalogoDto;
import com.sumaq.model.Categoria;
import com.sumaq.model.Producto;
import com.sumaq.repository.CategoriaRepository;
import com.sumaq.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CatalogoServiceTest {

    private final CategoriaRepository categoriaRepository = mock(CategoriaRepository.class);
    private final ProductoRepository productoRepository = mock(ProductoRepository.class);
    private final CatalogoService catalogoService = new CatalogoService(categoriaRepository, productoRepository);

    @Test
    void obtieneProductosDisponiblesDeLaCategoriaSeleccionada() {
        Categoria entradas = new Categoria("Entradas", "Para comenzar", 10);
        ReflectionTestUtils.setField(entradas, "id", 1L);
        Producto producto = new Producto(
                entradas,
                "Ceviche de hongos",
                "Hongos y cítricos andinos",
                new BigDecimal("28.00"),
                "/img/productos/entradas.svg");
        ReflectionTestUtils.setField(producto, "id", 7L);

        when(categoriaRepository.findByActivaTrueOrderByOrdenVisualAsc()).thenReturn(List.of(entradas));
        when(productoRepository.findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(1L))
                .thenReturn(List.of(producto));

        CatalogoDto catalogo = catalogoService.obtenerCatalogo(1L);

        assertThat(catalogo.categoriaSeleccionada().nombre()).isEqualTo("Entradas");
        assertThat(catalogo.productos()).singleElement()
                .satisfies(item -> {
                    assertThat(item.id()).isEqualTo(7L);
                    assertThat(item.nombre()).isEqualTo("Ceviche de hongos");
                    assertThat(item.precio()).isEqualByComparingTo("28.00");
                });
    }
}
