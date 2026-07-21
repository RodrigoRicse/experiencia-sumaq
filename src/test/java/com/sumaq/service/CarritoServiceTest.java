package com.sumaq.service;

import com.sumaq.dto.CarritoDto;
import com.sumaq.model.Categoria;
import com.sumaq.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarritoServiceTest {

    private final CarritoSesion carritoSesion = new CarritoSesion();
    private final ProductoService productoService = mock(ProductoService.class);
    private final CarritoService carritoService = new CarritoService(carritoSesion, productoService);

    @Test
    void agregaProductosYCalculaTotalConPreciosDelServidor() {
        Producto producto = new Producto(
                new Categoria("Entradas", "Para comenzar", 10),
                "Ceviche de hongos",
                "Hongos y cítricos andinos",
                new BigDecimal("28.00"),
                "/img/productos/entradas.svg");
        ReflectionTestUtils.setField(producto, "id", 7L);
        when(productoService.obtenerDisponible(7L)).thenReturn(producto);

        carritoService.agregar(7L);
        carritoService.agregar(7L);
        CarritoDto carrito = carritoService.obtener();

        assertThat(carrito.cantidadTotal()).isEqualTo(2);
        assertThat(carrito.total()).isEqualByComparingTo("56.00");
        assertThat(carrito.items()).singleElement()
                .satisfies(item -> assertThat(item.cantidad()).isEqualTo(2));
    }
}
