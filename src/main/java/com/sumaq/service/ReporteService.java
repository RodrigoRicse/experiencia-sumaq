package com.sumaq.service;

import com.sumaq.dto.ReporteAdminDto;
import com.sumaq.dto.VentaCategoriaDto;
import com.sumaq.repository.DetallePedidoRepository;
import com.sumaq.repository.PedidoRepository;
import com.sumaq.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReporteService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public ReporteService(
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Transactional(readOnly = true)
    public ReporteAdminDto obtenerResumen() {
        long pedidosAprobados = pedidoRepository.contarConPagoAprobado();
        BigDecimal ingresos = pedidoRepository.sumarIngresosAprobados();
        if (ingresos == null) {
            ingresos = BigDecimal.ZERO;
        }
        ingresos = ingresos.setScale(2, RoundingMode.HALF_UP);
        BigDecimal ticketPromedio = pedidosAprobados == 0
                ? BigDecimal.ZERO.setScale(2)
                : ingresos.divide(BigDecimal.valueOf(pedidosAprobados), 2, RoundingMode.HALF_UP);
        List<VentaCategoriaDto> ventas = detallePedidoRepository.resumirVentasAprobadasPorCategoria();

        return new ReporteAdminDto(
                pedidoRepository.count(),
                pedidosAprobados,
                ingresos,
                ticketPromedio,
                productoRepository.countByDisponibleTrue(),
                productoRepository.countByDisponibleFalse(),
                ventas);
    }
}
