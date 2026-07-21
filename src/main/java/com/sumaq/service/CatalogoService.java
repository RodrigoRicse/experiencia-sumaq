package com.sumaq.service;

import com.sumaq.dto.CatalogoDto;
import com.sumaq.dto.CategoriaCatalogoDto;
import com.sumaq.dto.ProductoCatalogoDto;
import com.sumaq.model.Categoria;
import com.sumaq.model.Producto;
import com.sumaq.repository.CategoriaRepository;
import com.sumaq.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogoService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CatalogoService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public CatalogoDto obtenerCatalogo(Long categoriaSolicitada) {
        List<Categoria> categoriasActivas = categoriaRepository.findByActivaTrueOrderByOrdenVisualAsc();
        List<CategoriaCatalogoDto> categorias = categoriasActivas.stream()
                .map(categoria -> new CategoriaCatalogoDto(categoria.getId(), categoria.getNombre()))
                .toList();

        CategoriaCatalogoDto seleccionada = seleccionarCategoria(categorias, categoriaSolicitada);
        List<ProductoCatalogoDto> productos = seleccionada == null
                ? List.of()
                : productoRepository.findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(seleccionada.id()).stream()
                        .map(this::aDto)
                        .toList();

        return new CatalogoDto(categorias, seleccionada, productos);
    }

    private CategoriaCatalogoDto seleccionarCategoria(
            List<CategoriaCatalogoDto> categorias, Long categoriaSolicitada) {
        if (categoriaSolicitada != null) {
            return categorias.stream()
                    .filter(categoria -> categoria.id().equals(categoriaSolicitada))
                    .findFirst()
                    .orElseGet(() -> categorias.stream().findFirst().orElse(null));
        }
        return categorias.stream().findFirst().orElse(null);
    }

    private ProductoCatalogoDto aDto(Producto producto) {
        return new ProductoCatalogoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getRutaImagen(),
                producto.getCategoria().getNombre(),
                producto.getCalorias());
    }
}
