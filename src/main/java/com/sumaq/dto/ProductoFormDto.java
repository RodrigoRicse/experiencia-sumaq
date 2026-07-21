package com.sumaq.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductoFormDto {

    @NotNull(message = "Selecciona una categoría")
    private Long categoriaId;

    @NotBlank(message = "Ingresa el nombre")
    @Size(max = 120, message = "El nombre admite hasta 120 caracteres")
    private String nombre;

    @NotBlank(message = "Ingresa la descripción")
    @Size(max = 500, message = "La descripción admite hasta 500 caracteres")
    private String descripcion;

    @NotNull(message = "Ingresa el precio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    @Digits(integer = 8, fraction = 2, message = "Usa como máximo dos decimales")
    private BigDecimal precio;

    @NotBlank(message = "Selecciona una imagen")
    @Pattern(
            regexp = "^/img/productos/(entradas|fondos|bebidas|postres)\\.svg$",
            message = "Selecciona una imagen local válida"
    )
    private String rutaImagen;

    @Min(value = 0, message = "Las calorías no pueden ser negativas")
    @Max(value = 5000, message = "Las calorías exceden el máximo permitido")
    private Integer calorias;

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }
}
