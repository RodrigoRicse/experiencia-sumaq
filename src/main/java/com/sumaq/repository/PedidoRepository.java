package com.sumaq.repository;

import com.sumaq.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByCodigoRecojo(String codigoRecojo);

    @EntityGraph(attributePaths = {"cliente", "estado", "detalles", "detalles.producto", "pago"})
    Optional<Pedido> findByCodigoRecojo(String codigoRecojo);

    @EntityGraph(attributePaths = {"cliente", "estado", "detalles", "pago"})
    List<Pedido> findByEstadoCodigoInOrderByCreadoEnAsc(List<String> codigosEstado);
}
