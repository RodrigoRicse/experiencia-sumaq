package com.sumaq.service;

import com.sumaq.dto.ClientePedidoDto;
import com.sumaq.dto.ItemPedidoDto;
import com.sumaq.dto.PedidoRegistradoDto;
import com.sumaq.dto.RegistroPedidoDto;
import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.exception.TransicionEstadoInvalidaException;
import com.sumaq.model.Cliente;
import com.sumaq.model.EstadoPago;
import com.sumaq.model.EstadoPedido;
import com.sumaq.model.Pago;
import com.sumaq.model.Pedido;
import com.sumaq.model.Producto;
import com.sumaq.repository.ClienteRepository;
import com.sumaq.repository.EstadoPedidoRepository;
import com.sumaq.repository.PedidoRepository;
import com.sumaq.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PedidoService {

    private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);
    private static final String PENDIENTE = "PENDIENTE";
    private static final String EN_PREPARACION = "EN_PREPARACION";
    private static final String LISTO = "LISTO";
    private static final String ENTREGADO = "ENTREGADO";
    private static final String CANCELADO = "CANCELADO";

    private static final Map<String, Set<String>> TRANSICIONES = Map.of(
            PENDIENTE, Set.of(EN_PREPARACION, CANCELADO),
            EN_PREPARACION, Set.of(LISTO, CANCELADO),
            LISTO, Set.of(ENTREGADO),
            ENTREGADO, Set.of(),
            CANCELADO, Set.of()
    );

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final EstadoPedidoRepository estadoPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;
    private final CodigoRecojoService codigoRecojoService;

    public PedidoService(
            ClienteRepository clienteRepository,
            ProductoRepository productoRepository,
            EstadoPedidoRepository estadoPedidoRepository,
            PedidoRepository pedidoRepository,
            ProductoService productoService,
            CodigoRecojoService codigoRecojoService
    ) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
        this.codigoRecojoService = codigoRecojoService;
    }

    @Transactional
    public PedidoRegistradoDto registrar(RegistroPedidoDto solicitud) {
        EstadoPedido estadoInicial = obtenerEstado(PENDIENTE);
        Cliente cliente = clienteRepository.save(crearCliente(solicitud.cliente()));
        Pedido pedido = new Pedido(cliente, estadoInicial, codigoRecojoService.generar(), solicitud.observaciones());

        for (ItemPedidoDto item : solicitud.items()) {
            Producto producto = productoRepository.findById(item.productoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + item.productoId()));
            productoService.validarDisponibilidad(producto);
            pedido.agregarDetalle(producto, item.cantidad(), item.observaciones());
        }

        Pago pago = Pago.simulado(
                pedido,
                solicitud.metodoPago(),
                pedido.getTotal(),
                Boolean.TRUE.equals(solicitud.pagoAprobado())
        );
        pedido.registrarPago(pago);
        Pedido guardado = pedidoRepository.save(pedido);

        LOG.info("Pedido creado: id={}, codigo={}, estado={}, pago={}",
                guardado.getId(), guardado.getCodigoRecojo(), guardado.getEstado().getCodigo(), pago.getEstado());

        return new PedidoRegistradoDto(
                guardado.getId(),
                guardado.getCodigoRecojo(),
                guardado.getTotal(),
                guardado.getEstado().getCodigo(),
                pago.getEstado()
        );
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorCodigoRecojo(String codigoRecojo) {
        return pedidoRepository.findByCodigoRecojo(codigoRecojo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado: " + codigoRecojo));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarParaCocina() {
        return pedidoRepository.findByEstadoCodigoInOrderByCreadoEnAsc(
                List.of(PENDIENTE, EN_PREPARACION, LISTO)
        );
    }

    @Transactional
    public Pedido cambiarEstado(Long pedidoId, String codigoNuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado: " + pedidoId));
        String estadoActual = pedido.getEstado().getCodigo();
        String nuevoEstadoNormalizado = codigoNuevoEstado.toUpperCase();

        if (!TRANSICIONES.getOrDefault(estadoActual, Set.of()).contains(nuevoEstadoNormalizado)) {
            throw new TransicionEstadoInvalidaException(
                    "No se permite cambiar el pedido de " + estadoActual + " a " + nuevoEstadoNormalizado
            );
        }
        if (ENTREGADO.equals(nuevoEstadoNormalizado)
                && (pedido.getPago() == null || pedido.getPago().getEstado() != EstadoPago.APROBADO)) {
            throw new TransicionEstadoInvalidaException("No se puede entregar un pedido sin pago aprobado");
        }

        pedido.cambiarEstado(obtenerEstado(nuevoEstadoNormalizado));
        Pedido guardado = pedidoRepository.save(pedido);
        LOG.info("Estado de pedido actualizado: id={}, codigo={}, anterior={}, nuevo={}",
                guardado.getId(), guardado.getCodigoRecojo(), estadoActual, nuevoEstadoNormalizado);
        return guardado;
    }

    private EstadoPedido obtenerEstado(String codigo) {
        return estadoPedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de pedido no configurado: " + codigo));
    }

    private Cliente crearCliente(ClientePedidoDto datos) {
        String email = datos.email() == null || datos.email().isBlank() ? null : datos.email().trim();
        return new Cliente(
                datos.nombres().trim(),
                datos.apellidos().trim(),
                datos.telefono().trim(),
                email
        );
    }
}
