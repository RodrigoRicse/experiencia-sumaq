package com.sumaq.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estado_pedido_id", nullable = false)
    private EstadoPedido estado;

    @NotBlank
    @Size(max = 20)
    @Column(name = "codigo_recojo", nullable = false, unique = true, length = 20)
    private String codigoRecojo;

    @Size(max = 500)
    @Column(length = 500)
    private String observaciones;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO.setScale(2);

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO.setScale(2);

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    @Version
    @Column(nullable = false)
    private long version;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<DetallePedido> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Pago pago;

    protected Pedido() {
    }

    public Pedido(Cliente cliente, EstadoPedido estado, String codigoRecojo, String observaciones) {
        this.cliente = cliente;
        this.estado = estado;
        this.codigoRecojo = codigoRecojo;
        this.observaciones = observaciones;
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

    public DetallePedido agregarDetalle(Producto producto, int cantidad, String observaciones) {
        DetallePedido detalle = new DetallePedido(this, producto, cantidad, observaciones);
        detalles.add(detalle);
        recalcularTotal();
        return detalle;
    }

    public BigDecimal recalcularTotal() {
        subtotal = detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);
        total = subtotal;
        return total;
    }

    public void registrarPago(Pago pago) {
        if (pago == null || pago.getPedido() != this) {
            throw new IllegalArgumentException("El pago debe pertenecer al pedido");
        }
        this.pago = pago;
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getCodigoRecojo() {
        return codigoRecojo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<DetallePedido> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    public Pago getPago() {
        return pago;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }
}
