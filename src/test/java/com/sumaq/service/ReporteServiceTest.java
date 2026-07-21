package com.sumaq.service;

import com.sumaq.dto.ReporteAdminDto;
import com.sumaq.dto.VentaCategoriaDto;
import com.sumaq.repository.DetallePedidoRepository;
import com.sumaq.repository.PedidoRepository;
import com.sumaq.repository.ProductoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReporteServiceTest {

    private final PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private final ProductoRepository productoRepository = mock(ProductoRepository.class);
    private final DetallePedidoRepository detalleRepository = mock(DetallePedidoRepository.class);
    private final ReporteService reporteService = new ReporteService(
            pedidoRepository, productoRepository, detalleRepository);

    @Test
    void calculaResumenSoloConVentasAprobadas() {
        when(pedidoRepository.count()).thenReturn(5L);
        when(pedidoRepository.contarConPagoAprobado()).thenReturn(3L);
        when(pedidoRepository.sumarIngresosAprobados()).thenReturn(new BigDecimal("150.00"));
        when(productoRepository.countByDisponibleTrue()).thenReturn(7L);
        when(productoRepository.countByDisponibleFalse()).thenReturn(2L);
        when(detalleRepository.resumirVentasAprobadasPorCategoria())
                .thenReturn(List.of(new VentaCategoriaDto("Fondos", 4L, new BigDecimal("120.00"))));

        ReporteAdminDto reporte = reporteService.obtenerResumen();

        assertThat(reporte.pedidosTotales()).isEqualTo(5);
        assertThat(reporte.ingresosAprobados()).isEqualByComparingTo("150.00");
        assertThat(reporte.ticketPromedio()).isEqualByComparingTo("50.00");
        assertThat(reporte.ventasPorCategoria()).singleElement()
                .satisfies(venta -> assertThat(venta.categoria()).isEqualTo("Fondos"));
    }

    @Test
    void usaCeroComoTicketPromedioCuandoNoHayVentas() {
        when(pedidoRepository.sumarIngresosAprobados()).thenReturn(null);
        when(detalleRepository.resumirVentasAprobadasPorCategoria()).thenReturn(List.of());

        assertThat(reporteService.obtenerResumen().ticketPromedio()).isEqualByComparingTo("0.00");
    }
}
