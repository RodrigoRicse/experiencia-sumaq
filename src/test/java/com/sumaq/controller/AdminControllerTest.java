package com.sumaq.controller;

import com.sumaq.dto.ProductoFormDto;
import com.sumaq.dto.ReporteAdminDto;
import com.sumaq.service.ProductoService;
import com.sumaq.service.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

class AdminControllerTest {

    private ProductoService productoService;
    private ReporteService reporteService;
    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        productoService = mock(ProductoService.class);
        reporteService = mock(ReporteService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(productoService, reporteService)).build();
    }

    @Test
    void muestraResumenYProductos() throws Exception {
        ReporteAdminDto reporte = new ReporteAdminDto(
                2, 1, new BigDecimal("38.00"), new BigDecimal("38.00"), 8, 0, List.of());
        when(reporteService.obtenerResumen()).thenReturn(reporte);
        when(productoService.listarParaAdministracion()).thenReturn(List.of());

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/panel"))
                .andExpect(model().attribute("reporte", reporte))
                .andExpect(model().attribute("productos", List.of()));
    }

    @Test
    void rechazaFormularioDeProductoInvalido() throws Exception {
        mockMvc.perform(post("/admin/productos")
                        .param("nombre", "")
                        .param("precio", "0")
                        .param("rutaImagen", "https://externo.test/imagen.jpg"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/producto-form"))
                .andExpect(model().attributeHasFieldErrors(
                        "productoForm", "nombre", "categoriaId", "descripcion", "precio", "rutaImagen"));
    }

    @Test
    void cambiaDisponibilidadMedianteServicio() throws Exception {
        mockMvc.perform(post("/admin/productos/7/disponibilidad").param("disponible", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attributeExists("mensaje"));

        verify(productoService).cambiarDisponibilidad(7L, false);
    }
}
