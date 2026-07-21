package com.sumaq.controller;

import com.sumaq.dto.CarritoDto;
import com.sumaq.dto.CarritoItemDto;
import com.sumaq.service.CarritoService;
import com.sumaq.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class PedidoWebControllerTest {

    private CarritoService carritoService;
    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        carritoService = mock(CarritoService.class);
        PedidoService pedidoService = mock(PedidoService.class);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PedidoWebController(carritoService, pedidoService))
                .setValidator(validator)
                .build();
    }

    @Test
    void rechazaCheckoutConDatosDeClienteInvalidos() throws Exception {
        CarritoItemDto item = new CarritoItemDto(
                1L, "Ceviche", new BigDecimal("28.00"), "/img/productos/entradas.svg", 1,
                new BigDecimal("28.00"));
        when(carritoService.estaVacio()).thenReturn(false);
        when(carritoService.obtener()).thenReturn(new CarritoDto(List.of(item), 1, new BigDecimal("28.00")));

        mockMvc.perform(post("/pedido/confirmar")
                        .param("nombres", "")
                        .param("apellidos", "")
                        .param("telefono", "abc")
                        .param("email", "correo-invalido")
                        .param("metodoPago", "YAPE")
                        .param("pagoAprobado", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("cliente/checkout"))
                .andExpect(model().attributeHasFieldErrors(
                        "checkoutForm", "nombres", "apellidos", "telefono", "email"));
    }
}
