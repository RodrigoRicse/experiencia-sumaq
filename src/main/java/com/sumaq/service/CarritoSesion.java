package com.sumaq.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
@SessionScope
public class CarritoSesion {

    private static final int CANTIDAD_MAXIMA = 20;
    private final Map<Long, Integer> cantidades = new LinkedHashMap<>();

    public void agregar(Long productoId) {
        cantidades.compute(productoId, (id, actual) ->
                Math.min(CANTIDAD_MAXIMA, actual == null ? 1 : actual + 1));
    }

    public void actualizar(Long productoId, int cantidad) {
        if (cantidad <= 0) {
            cantidades.remove(productoId);
            return;
        }
        cantidades.put(productoId, Math.min(cantidad, CANTIDAD_MAXIMA));
    }

    public void eliminar(Long productoId) {
        cantidades.remove(productoId);
    }

    public Map<Long, Integer> obtenerCantidades() {
        return Collections.unmodifiableMap(cantidades);
    }

    public int cantidadTotal() {
        return cantidades.values().stream()
                .mapToInt(cantidad -> Objects.requireNonNull(
                        cantidad, "La cantidad del carrito no puede ser nula").intValue())
                .sum();
    }

    public boolean estaVacio() {
        return cantidades.isEmpty();
    }

    public void vaciar() {
        cantidades.clear();
    }
}
