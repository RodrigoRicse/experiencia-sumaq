package com.sumaq.controller;

import com.sumaq.dto.CatalogoDto;
import com.sumaq.dto.CategoriaCatalogoDto;
import com.sumaq.dto.CarritoDto;
import com.sumaq.service.CarritoService;
import com.sumaq.service.CatalogoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ClienteControllerTest {

    private CatalogoService catalogoService;
    private CarritoService carritoService;
    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        catalogoService = mock(CatalogoService.class);
        carritoService = mock(CarritoService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ClienteController(catalogoService, carritoService)).build();
    }

    @Test
    void muestraPaginaDeInicio() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("cliente/inicio"))
                .andExpect(model().attribute("paginaActiva", "inicio"));
    }

    @Test
    void muestraMenuConCatalogoDeBaseDeDatos() throws Exception {
        CategoriaCatalogoDto categoria = new CategoriaCatalogoDto(1L, "Entradas");
        CatalogoDto catalogo = new CatalogoDto(List.of(categoria), categoria, List.of());
        when(catalogoService.obtenerCatalogo(1L)).thenReturn(catalogo);
        when(carritoService.obtener()).thenReturn(new CarritoDto(List.of(), 0, BigDecimal.ZERO.setScale(2)));

        mockMvc.perform(get("/menu").param("categoria", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cliente/menu"))
                .andExpect(model().attribute("catalogo", catalogo))
                .andExpect(model().attribute("paginaActiva", "menu"));
    }
}
