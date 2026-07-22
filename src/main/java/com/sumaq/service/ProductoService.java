package com.sumaq.service;

import com.sumaq.dto.CategoriaOpcionDto;
import com.sumaq.dto.ProductoAdminDto;
import com.sumaq.dto.ProductoFormDto;
import com.sumaq.exception.ProductoNoDisponibleException;
import com.sumaq.exception.RecursoNoEncontradoException;
import com.sumaq.model.Categoria;
import com.sumaq.model.Producto;
import com.sumaq.repository.CategoriaRepository;
import com.sumaq.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoAdminDto> listarParaAdministracion() {
        return productoRepository.findAllByOrderByCategoriaOrdenVisualAscNombreAsc().stream()
                .map(this::aAdminDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaOpcionDto> listarCategoriasActivas() {
        return categoriaRepository.findByActivaTrueOrderByOrdenVisualAsc().stream()
                .map(categoria -> new CategoriaOpcionDto(categoria.getId(), categoria.getNombre()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Producto> listarDisponibles() {
        return productoRepository.findByDisponibleTrueOrderByCategoriaOrdenVisualAscNombreAsc();
    }

    @Transactional(readOnly = true)
    public Producto obtenerDisponible(Long productoId) {
        Producto producto = obtener(productoId);
        validarDisponibilidad(producto);
        return producto;
    }

    @Transactional(readOnly = true)
    public Producto obtener(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + productoId));
    }

    @Transactional(readOnly = true)
    public ProductoFormDto obtenerFormulario(Long productoId) {
        Producto producto = obtener(productoId);
        ProductoFormDto formulario = new ProductoFormDto();
        formulario.setCategoriaId(producto.getCategoria().getId());
        formulario.setNombre(producto.getNombre());
        formulario.setDescripcion(producto.getDescripcion());
        formulario.setPrecio(producto.getPrecio());
        formulario.setRutaImagen(producto.getRutaImagen());
        formulario.setCalorias(producto.getCalorias());
        return formulario;
    }

    public void validarDisponibilidad(Producto producto) {
        if (!producto.isDisponible()) {
            throw new ProductoNoDisponibleException("El producto no está disponible: " + producto.getNombre());
        }
    }

    @Transactional
    public Producto cambiarDisponibilidad(Long productoId, boolean disponible) {
        Producto producto = obtener(productoId);
        producto.cambiarDisponibilidad(disponible);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto crear(ProductoFormDto formulario) {
        String nombre = normalizar(formulario.getNombre());
        validarNombreUnico(nombre, null);
        Categoria categoria = obtenerCategoria(formulario.getCategoriaId());
        Producto producto = new Producto(
                categoria,
                nombre,
                normalizar(formulario.getDescripcion()),
                formulario.getPrecio(),
                formulario.getRutaImagen());
        producto.actualizar(categoria, nombre, normalizar(formulario.getDescripcion()),
                formulario.getPrecio(), formulario.getRutaImagen(), formulario.getCalorias());
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long productoId, ProductoFormDto formulario) {
        Producto producto = obtener(productoId);
        String nombre = normalizar(formulario.getNombre());
        validarNombreUnico(nombre, productoId);
        Categoria categoria = obtenerCategoria(formulario.getCategoriaId());
        producto.actualizar(
                categoria,
                nombre,
                normalizar(formulario.getDescripcion()),
                formulario.getPrecio(),
                formulario.getRutaImagen(),
                formulario.getCalorias());
        return productoRepository.save(producto);
    }

    private Categoria obtenerCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .filter(categoria -> Objects.requireNonNull(
                        categoria, "La categoría consultada no puede ser nula").isActiva())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + categoriaId));
    }

    private void validarNombreUnico(String nombre, Long productoId) {
        boolean duplicado = productoId == null
                ? productoRepository.existsByNombreIgnoreCase(nombre)
                : productoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, productoId);
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
    }

    private String normalizar(String valor) {
        return valor == null ? null : valor.trim().replaceAll("\\s+", " ");
    }

    private ProductoAdminDto aAdminDto(Producto producto) {
        return new ProductoAdminDto(
                producto.getId(),
                producto.getCategoria().getNombre(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getRutaImagen(),
                producto.isDisponible(),
                producto.getCalorias());
    }
}
