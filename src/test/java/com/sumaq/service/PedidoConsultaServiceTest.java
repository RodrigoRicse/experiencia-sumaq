package com.sumaq.service;

import com.sumaq.dto.PedidoCajaDto;
import com.sumaq.dto.PedidoCocinaDto;
import com.sumaq.model.Categoria;
import com.sumaq.model.Cliente;
import com.sumaq.model.EstadoPedido;
import com.sumaq.model.MetodoPago;
import com.sumaq.model.Pago;
import com.sumaq.model.Pedido;
import com.sumaq.model.Producto;
import com.sumaq.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoConsultaServiceTest {

    private final PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private final Clock clock = Clock.fixed(Instant.parse("2026-07-21T17:00:00Z"), ZoneId.of("UTC"));
    private final PedidoConsultaService service = new PedidoConsultaService(pedidoRepository, clock);

    @Test
    void preparaTicketsDeCocinaConTiempoYDetalle() {
        Pedido pedido = crearPedido(new EstadoPedido("PENDIENTE", "Pendiente", 10));
        when(pedidoRepository.findByEstadoCodigoInOrderByCreadoEnAsc(
                List.of("PENDIENTE", "EN_PREPARACION", "LISTO"))).thenReturn(List.of(pedido));

        List<PedidoCocinaDto> resultado = service.listarCocina();

        assertThat(resultado).singleElement().satisfies(ticket -> {
            assertThat(ticket.codigoRecojo()).isEqualTo("SUMAQ-ABC123");
            assertThat(ticket.minutosEspera()).isEqualTo(30);
            assertThat(ticket.detalles()).singleElement()
                    .satisfies(detalle -> assertThat(detalle.producto()).isEqualTo("Ceviche de hongos"));
        });
    }

    @Test
    void habilitaEntregaCuandoPedidoEstaListoYPagado() {
        Pedido pedido = crearPedido(new EstadoPedido("LISTO", "Listo", 30));
        pedido.registrarPago(Pago.simulado(pedido, MetodoPago.YAPE, pedido.getTotal(), true));
        when(pedidoRepository.findByCodigoRecojo("SUMAQ-ABC123")).thenReturn(Optional.of(pedido));

        PedidoCajaDto resultado = service.buscarParaCaja(" sumaq-abc123 ");

        assertThat(resultado.puedeEntregarse()).isTrue();
        assertThat(resultado.cliente()).isEqualTo("Rosa Flores");
        assertThat(resultado.total()).isEqualByComparingTo("56.00");
    }

    private Pedido crearPedido(EstadoPedido estado) {
        Categoria categoria = new Categoria("Entradas", "Para comenzar", 10);
        Producto producto = new Producto(
                categoria,
                "Ceviche de hongos",
                "Hongos con cítricos",
                new BigDecimal("28.00"),
                "/img/productos/entradas.svg");
        Cliente cliente = new Cliente("Rosa", "Flores", "999999999", null);
        Pedido pedido = new Pedido(cliente, estado, "SUMAQ-ABC123", "Recojo en tienda");
        pedido.agregarDetalle(producto, 2, "Sin ají");
        ReflectionTestUtils.setField(pedido, "id", 8L);
        ReflectionTestUtils.setField(pedido, "creadoEn", LocalDateTime.of(2026, 7, 21, 16, 30));
        return pedido;
    }
}
