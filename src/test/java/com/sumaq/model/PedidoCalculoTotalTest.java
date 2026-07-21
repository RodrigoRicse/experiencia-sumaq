package com.sumaq.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoCalculoTotalTest {

    @Test
    void calculaElTotalDesdeLosDetalles() {
        Categoria categoria = new Categoria("Entradas", "Entradas", 10);
        Producto ceviche = new Producto(categoria, "Ceviche", "Ceviche andino", new BigDecimal("28.00"), "/img/ceviche.webp");
        Producto hummus = new Producto(categoria, "Hummus", "Hummus de tarwi", new BigDecimal("22.00"), "/img/hummus.webp");
        Cliente cliente = new Cliente("Ada", "Lovelace", "999999999", "ada@example.com");
        EstadoPedido pendiente = new EstadoPedido("PENDIENTE", "Pendiente", 10);
        Pedido pedido = new Pedido(cliente, pendiente, "SUMAQ-ABC123", null);

        pedido.agregarDetalle(ceviche, 2, null);
        pedido.agregarDetalle(hummus, 1, null);

        assertThat(pedido.getSubtotal()).isEqualByComparingTo("78.00");
        assertThat(pedido.getTotal()).isEqualByComparingTo("78.00");
    }
}
