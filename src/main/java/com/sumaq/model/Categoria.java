package com.sumaq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Size(max = 200)
    @Column(length = 200)
    private String descripcion;

    @Min(0)
    @Column(name = "orden_visual", nullable = false)
    private int ordenVisual;

    @Column(nullable = false)
    private boolean activa = true;

    protected Categoria() {
    }

    public Categoria(String nombre, String descripcion, int ordenVisual) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ordenVisual = ordenVisual;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getOrdenVisual() {
        return ordenVisual;
    }

    public boolean isActiva() {
        return activa;
    }
}
