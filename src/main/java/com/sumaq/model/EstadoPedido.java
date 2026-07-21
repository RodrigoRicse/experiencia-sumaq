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
@Table(name = "estados_pedido")
public class EstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Min(0)
    @Column(name = "orden_flujo", nullable = false)
    private int ordenFlujo;

    protected EstadoPedido() {
    }

    public EstadoPedido(String codigo, String nombre, int ordenFlujo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ordenFlujo = ordenFlujo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getOrdenFlujo() {
        return ordenFlujo;
    }
}
