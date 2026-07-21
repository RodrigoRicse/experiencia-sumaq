package com.sumaq.repository;

import com.sumaq.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long> {
    Optional<EstadoPedido> findByCodigo(String codigo);
}
