package com.sumaq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private Pedido pedido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MetodoPago metodo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPago estado;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Size(max = 60)
    @Column(unique = true, length = 60)
    private String referencia;

    @Column(name = "procesado_en")
    private LocalDateTime procesadoEn;

    protected Pago() {
    }

    private Pago(Pedido pedido, MetodoPago metodo, BigDecimal monto) {
        this.pedido = pedido;
        this.metodo = metodo;
        this.monto = monto;
        this.estado = EstadoPago.PENDIENTE;
    }

    public static Pago simulado(Pedido pedido, MetodoPago metodo, BigDecimal monto, boolean aprobado) {
        Pago pago = new Pago(pedido, metodo, monto);
        pago.estado = aprobado ? EstadoPago.APROBADO : EstadoPago.RECHAZADO;
        pago.referencia = "SIM-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        pago.procesadoEn = LocalDateTime.now();
        return pago;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getReferencia() {
        return referencia;
    }

    public LocalDateTime getProcesadoEn() {
        return procesadoEn;
    }
}
