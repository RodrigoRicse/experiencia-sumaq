package com.sumaq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, unique = true, length = 120)
    private String nombre;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @NotBlank
    @Size(max = 255)
    @Column(name = "ruta_imagen", nullable = false, length = 255)
    private String rutaImagen;

    @Column(nullable = false)
    private boolean disponible = true;

    @Min(0)
    private Integer calorias;

    @DecimalMin("0.00")
    @Column(name = "proteinas_gramos", precision = 6, scale = 2)
    private BigDecimal proteinasGramos;

    @DecimalMin("0.00")
    @Column(name = "carbohidratos_gramos", precision = 6, scale = 2)
    private BigDecimal carbohidratosGramos;

    @DecimalMin("0.00")
    @Column(name = "grasas_gramos", precision = 6, scale = 2)
    private BigDecimal grasasGramos;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    protected Producto() {
    }

    public Producto(Categoria categoria, String nombre, String descripcion, BigDecimal precio, String rutaImagen) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
    }

    @PrePersist
    void antesDeCrear() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = creadoEn;
    }

    @PreUpdate
    void antesDeActualizar() {
        actualizadoEn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public BigDecimal getProteinasGramos() {
        return proteinasGramos;
    }

    public BigDecimal getCarbohidratosGramos() {
        return carbohidratosGramos;
    }

    public BigDecimal getGrasasGramos() {
        return grasasGramos;
    }

    public void cambiarDisponibilidad(boolean disponible) {
        this.disponible = disponible;
    }

    public void actualizar(Categoria categoria, String nombre, String descripcion, BigDecimal precio, String rutaImagen) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
    }
}
