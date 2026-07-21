package com.sumaq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotBlank
    @Size(max = 120)
    @Column(name = "nombre_producto", nullable = false, length = 120)
    private String nombreProducto;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Min(1)
    @Column(nullable = false)
    private int cantidad;

    @Size(max = 300)
    @Column(length = 300)
    private String observaciones;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    protected DetallePedido() {
    }

    DetallePedido(Pedido pedido, Producto producto, int cantidad, String observaciones) {
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.pedido = pedido;
        this.producto = producto;
        this.nombreProducto = producto.getNombre();
        this.precioUnitario = producto.getPrecio();
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad)).setScale(2);
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
