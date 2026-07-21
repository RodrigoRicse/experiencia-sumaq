package com.sumaq.repository;

import com.sumaq.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAllByOrderByCategoriaOrdenVisualAscNombreAsc();

    List<Producto> findByDisponibleTrueOrderByCategoriaOrdenVisualAscNombreAsc();

    List<Producto> findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(Long categoriaId);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    long countByDisponibleTrue();

    long countByDisponibleFalse();
}
