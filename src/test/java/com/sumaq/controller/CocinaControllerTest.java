package com.sumaq.controller;

import com.sumaq.service.PedidoConsultaService;
import com.sumaq.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class CocinaControllerTest {

    private PedidoConsultaService consultaService;
    private PedidoService pedidoService;
    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        consultaService = mock(PedidoConsultaService.class);
        pedidoService = mock(PedidoService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CocinaController(consultaService, pedidoService)).build();
    }

    @Test
    void muestraTableroSeparadoPorEstados() throws Exception {
        when(consultaService.listarCocina()).thenReturn(List.of());

        mockMvc.perform(get("/cocina"))
                .andExpect(status().isOk())
                .andExpect(view().name("cocina/panel"))
                .andExpect(model().attribute("pendientes", List.of()))
                .andExpect(model().attribute("enPreparacion", List.of()))
                .andExpect(model().attribute("listos", List.of()));
    }

    @Test
    void iniciaPreparacionMedianteServicioDeDominio() throws Exception {
        mockMvc.perform(post("/cocina/pedidos/15/iniciar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cocina"))
                .andExpect(flash().attributeExists("mensaje"));

        verify(pedidoService).cambiarEstado(15L, "EN_PREPARACION");
    }
}
