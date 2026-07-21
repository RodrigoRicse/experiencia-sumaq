package com.sumaq.repository;

import com.sumaq.dto.VentaCategoriaDto;
import com.sumaq.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    @Query("""
            select new com.sumaq.dto.VentaCategoriaDto(
                d.producto.categoria.nombre,
                sum(d.cantidad),
                sum(d.subtotal))
            from DetallePedido d
            where d.pedido.pago.estado = com.sumaq.model.EstadoPago.APROBADO
            group by d.producto.categoria.nombre
            order by sum(d.subtotal) desc
            """)
    List<VentaCategoriaDto> resumirVentasAprobadasPorCategoria();
}
