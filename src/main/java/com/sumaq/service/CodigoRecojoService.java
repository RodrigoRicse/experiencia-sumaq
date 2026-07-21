package com.sumaq.service;

import com.sumaq.exception.CodigoRecojoNoDisponibleException;
import com.sumaq.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class CodigoRecojoService {

    private static final String CARACTERES = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int MAX_INTENTOS = 20;

    private final PedidoRepository pedidoRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public CodigoRecojoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public String generar() {
        for (int intento = 0; intento < MAX_INTENTOS; intento++) {
            String codigo = "SUMAQ-" + generarSufijo(6);
            if (!pedidoRepository.existsByCodigoRecojo(codigo)) {
                return codigo;
            }
        }
        throw new CodigoRecojoNoDisponibleException("No fue posible generar un código de recojo único");
    }

    private String generarSufijo(int longitud) {
        StringBuilder resultado = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            resultado.append(CARACTERES.charAt(secureRandom.nextInt(CARACTERES.length())));
        }
        return resultado.toString();
    }
}
