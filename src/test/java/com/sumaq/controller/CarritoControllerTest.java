package com.sumaq.controller;

import com.sumaq.dto.CarritoDto;
import com.sumaq.dto.CarritoItemDto;
import com.sumaq.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarritoControllerTest {

    private CarritoService carritoService;
    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        carritoService = mock(CarritoService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CarritoController(carritoService)).build();
    }

    @Test
    void actualizaCantidadYRegresaAlCheckout() throws Exception {
        mockMvc.perform(post("/carrito/items/7/cantidad")
                        .param("cantidad", "2")
                        .param("origen", "checkout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pedido/checkout"));

        verify(carritoService).actualizar(7L, 2);
    }

    @Test
    void actualizaCantidadDesdeCheckoutSinRecargarLaPagina() throws Exception {
        CarritoItemDto item = new CarritoItemDto(
                7L, "Ají de gallina", new BigDecimal("24.00"), "/img/productos/fondos.svg", 2,
                new BigDecimal("48.00"));
        when(carritoService.obtener()).thenReturn(new CarritoDto(List.of(item), 2, new BigDecimal("48.00")));

        mockMvc.perform(post("/carrito/items/7/cantidad")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .param("cantidad", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].cantidad").value(2))
                .andExpect(jsonPath("$.cantidadTotal").value(2))
                .andExpect(jsonPath("$.total").value(48.00));

        verify(carritoService).actualizar(7L, 2);
    }

    @Test
    void eliminaProductoDesdeCheckoutYDevuelveCarritoVacio() throws Exception {
        when(carritoService.obtener()).thenReturn(new CarritoDto(List.of(), 0, BigDecimal.ZERO));

        mockMvc.perform(post("/carrito/items/7/eliminar")
                        .header("X-Requested-With", "XMLHttpRequest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.cantidadTotal").value(0));

        verify(carritoService).eliminar(7L);
    }
}
