package com.sumaq.controller;

import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.service.PedidoConsultaService;
import com.sumaq.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class CajaControllerTest {

    private PedidoConsultaService consultaService;
    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        consultaService = mock(PedidoConsultaService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CajaController(consultaService, mock(PedidoService.class)))
                .build();
    }

    @Test
    void muestraMensajeAmigableCuandoCodigoNoExiste() throws Exception {
        when(consultaService.buscarParaCaja("SUMAQ-NOEXIS"))
                .thenThrow(new RecursoNoEncontradoException("No existe el pedido"));

        mockMvc.perform(get("/caja").param("codigo", "SUMAQ-NOEXIS"))
                .andExpect(status().isOk())
                .andExpect(view().name("caja/panel"))
                .andExpect(model().attribute("error", "No existe el pedido"));
    }
}
