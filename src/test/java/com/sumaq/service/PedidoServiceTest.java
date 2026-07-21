package com.sumaq.service;

import com.sumaq.dto.ClientePedidoDto;
import com.sumaq.dto.ItemPedidoDto;
import com.sumaq.dto.PedidoRegistradoDto;
import com.sumaq.dto.RegistroPedidoDto;
import com.sumaq.model.Categoria;
import com.sumaq.model.Cliente;
import com.sumaq.model.EstadoPago;
import com.sumaq.model.EstadoPedido;
import com.sumaq.model.MetodoPago;
import com.sumaq.model.Pedido;
import com.sumaq.model.Producto;
import com.sumaq.repository.ClienteRepository;
import com.sumaq.repository.EstadoPedidoRepository;
import com.sumaq.repository.PedidoRepository;
import com.sumaq.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private EstadoPedidoRepository estadoPedidoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ProductoService productoService;
    @Mock
    private CodigoRecojoService codigoRecojoService;

    private PedidoService pedidoService;

    @BeforeEach
    void configurar() {
        pedidoService = new PedidoService(
                clienteRepository,
                productoRepository,
                estadoPedidoRepository,
                pedidoRepository,
                productoService,
                codigoRecojoService
        );
    }

    @Test
    void registraUnPedidoConPagoAprobado() {
        EstadoPedido pendiente = new EstadoPedido("PENDIENTE", "Pendiente", 10);
        Producto producto = new Producto(
                new Categoria("Entradas", "Entradas", 10),
                "Ceviche",
                "Ceviche de hongos",
                new BigDecimal("28.00"),
                "/img/ceviche.webp"
        );
        RegistroPedidoDto solicitud = new RegistroPedidoDto(
                new ClientePedidoDto("María", "Quispe", "999999999", "maria@example.com"),
                List.of(new ItemPedidoDto(1L, 2, "Sin ají")),
                "Recojo en tienda",
                MetodoPago.YAPE,
                true
        );
        when(estadoPedidoRepository.findByCodigo("PENDIENTE")).thenReturn(Optional.of(pendiente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
        when(codigoRecojoService.generar()).thenReturn("SUMAQ-ABC123");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        PedidoRegistradoDto resultado = pedidoService.registrar(solicitud);

        assertThat(resultado.codigoRecojo()).isEqualTo("SUMAQ-ABC123");
        assertThat(resultado.total()).isEqualByComparingTo("56.00");
        assertThat(resultado.estadoPedido()).isEqualTo("PENDIENTE");
        assertThat(resultado.estadoPago()).isEqualTo(EstadoPago.APROBADO);
    }

    @Test
    void cambiaPedidoPendienteAEnPreparacion() {
        EstadoPedido pendiente = new EstadoPedido("PENDIENTE", "Pendiente", 10);
        EstadoPedido preparando = new EstadoPedido("EN_PREPARACION", "En preparación", 20);
        Pedido pedido = new Pedido(
                new Cliente("Luis", "Ramos", "988888888", null),
                pendiente,
                "SUMAQ-XYZ789",
                null
        );
        when(pedidoRepository.findById(5L)).thenReturn(Optional.of(pedido));
        when(estadoPedidoRepository.findByCodigo("EN_PREPARACION")).thenReturn(Optional.of(preparando));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido actualizado = pedidoService.cambiarEstado(5L, "EN_PREPARACION");

        assertThat(actualizado.getEstado().getCodigo()).isEqualTo("EN_PREPARACION");
    }
}
