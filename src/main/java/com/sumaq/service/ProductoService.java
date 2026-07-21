package com.sumaq.service;

import com.sumaq.exception.ProductoNoDisponibleException;
import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.model.Producto;
import com.sumaq.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listarDisponibles() {
        return productoRepository.findByDisponibleTrueOrderByCategoriaOrdenVisualAscNombreAsc();
    }

    @Transactional(readOnly = true)
    public Producto obtenerDisponible(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + productoId));
        validarDisponibilidad(producto);
        return producto;
    }

    public void validarDisponibilidad(Producto producto) {
        if (!producto.isDisponible()) {
            throw new ProductoNoDisponibleException("El producto no está disponible: " + producto.getNombre());
        }
    }

    @Transactional
    public Producto cambiarDisponibilidad(Long productoId, boolean disponible) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + productoId));
        producto.cambiarDisponibilidad(disponible);
        return productoRepository.save(producto);
    }
}
