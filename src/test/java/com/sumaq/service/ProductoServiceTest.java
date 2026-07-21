package com.sumaq.service;

import com.sumaq.exception.ProductoNoDisponibleException;
import com.sumaq.model.Categoria;
import com.sumaq.model.Producto;
import com.sumaq.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void rechazaUnProductoNoDisponible() {
        Producto producto = new Producto(
                new Categoria("Fondos", "Fondos", 20),
                "Quinotto",
                "Quinotto de setas",
                new BigDecimal("38.00"),
                "/img/quinotto.webp"
        );
        producto.cambiarDisponibilidad(false);
        when(productoRepository.findById(10L)).thenReturn(Optional.of(producto));

        assertThatThrownBy(() -> productoService.obtenerDisponible(10L))
                .isInstanceOf(ProductoNoDisponibleException.class)
                .hasMessageContaining("Quinotto");
    }
}
