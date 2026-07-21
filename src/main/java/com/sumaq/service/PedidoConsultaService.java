package com.sumaq.service;

import com.sumaq.dto.DetalleOperacionDto;
import com.sumaq.dto.PedidoCajaDto;
import com.sumaq.dto.PedidoCocinaDto;
import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.model.DetallePedido;
import com.sumaq.model.Pedido;
import com.sumaq.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class PedidoConsultaService {

    private static final List<String> ESTADOS_COCINA = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");

    private final PedidoRepository pedidoRepository;
    private final Clock clock;

    @Autowired
    public PedidoConsultaService(PedidoRepository pedidoRepository) {
        this(pedidoRepository, Clock.systemDefaultZone());
    }

    PedidoConsultaService(PedidoRepository pedidoRepository, Clock clock) {
        this.pedidoRepository = pedidoRepository;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public List<PedidoCocinaDto> listarCocina() {
        LocalDateTime ahora = LocalDateTime.now(clock);
        return pedidoRepository.findByEstadoCodigoInOrderByCreadoEnAsc(ESTADOS_COCINA).stream()
                .map(pedido -> aCocina(pedido, ahora))
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoCajaDto buscarParaCaja(String codigoRecojo) {
        String codigo = codigoRecojo == null ? "" : codigoRecojo.trim().toUpperCase(Locale.ROOT);
        Pedido pedido = pedidoRepository.findByCodigoRecojo(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un pedido con el código " + codigo));
        return new PedidoCajaDto(
                pedido.getId(),
                pedido.getCodigoRecojo(),
                pedido.getCliente().getNombres() + " " + pedido.getCliente().getApellidos(),
                pedido.getCliente().getTelefono(),
                pedido.getTotal(),
                pedido.getEstado().getCodigo(),
                pedido.getPago().getEstado(),
                pedido.getPago().getMetodo(),
                pedido.getCreadoEn(),
                detalles(pedido));
    }

    private PedidoCocinaDto aCocina(Pedido pedido, LocalDateTime ahora) {
        long minutos = Math.max(0, Duration.between(pedido.getCreadoEn(), ahora).toMinutes());
        return new PedidoCocinaDto(
                pedido.getId(),
                pedido.getCodigoRecojo(),
                pedido.getEstado().getCodigo(),
                pedido.getCreadoEn(),
                minutos,
                pedido.getObservaciones(),
                detalles(pedido));
    }

    private List<DetalleOperacionDto> detalles(Pedido pedido) {
        return pedido.getDetalles().stream().map(this::aDetalle).toList();
    }

    private DetalleOperacionDto aDetalle(DetallePedido detalle) {
        return new DetalleOperacionDto(
                detalle.getNombreProducto(),
                detalle.getCantidad(),
                detalle.getObservaciones());
    }
}
