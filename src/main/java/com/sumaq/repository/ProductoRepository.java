package com.sumaq.repository;

import com.sumaq.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByDisponibleTrueOrderByCategoriaOrdenVisualAscNombreAsc();

    List<Producto> findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(Long categoriaId);
}
