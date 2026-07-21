package com.sumaq.service;

import com.sumaq.exception.ProductoNoDisponibleException;
import com.sumaq.dto.ProductoFormDto;
import com.sumaq.model.Categoria;
import com.sumaq.model.Producto;
import com.sumaq.repository.CategoriaRepository;
import com.sumaq.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

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

    @Test
    void creaProductoNormalizandoSusDatos() {
        Categoria categoria = new Categoria("Postres", "Postres", 40);
        ProductoFormDto formulario = formularioValido();
        when(categoriaRepository.findById(4L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto creado = productoService.crear(formulario);

        assertThat(creado.getNombre()).isEqualTo("Torta de cacao");
        assertThat(creado.getDescripcion()).isEqualTo("Cacao peruano y frutos andinos");
        assertThat(creado.getCalorias()).isEqualTo(320);
        verify(productoRepository).existsByNombreIgnoreCase("Torta de cacao");
    }

    @Test
    void rechazaNombreDuplicadoAlCrear() {
        ProductoFormDto formulario = formularioValido();
        when(productoRepository.existsByNombreIgnoreCase("Torta de cacao")).thenReturn(true);

        assertThatThrownBy(() -> productoService.crear(formulario))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe");
    }

    private ProductoFormDto formularioValido() {
        ProductoFormDto formulario = new ProductoFormDto();
        formulario.setCategoriaId(4L);
        formulario.setNombre("  Torta   de cacao ");
        formulario.setDescripcion(" Cacao peruano   y frutos andinos ");
        formulario.setPrecio(new BigDecimal("24.50"));
        formulario.setRutaImagen("/img/productos/postres.svg");
        formulario.setCalorias(320);
        return formulario;
    }
}
