package com.sumaq.service;

import com.sumaq.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CodigoRecojoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Test
    void generaUnCodigoUnicoConFormatoEsperado() {
        when(pedidoRepository.existsByCodigoRecojo(anyString())).thenReturn(false);
        CodigoRecojoService servicio = new CodigoRecojoService(pedidoRepository);

        String codigo = servicio.generar();

        assertThat(codigo).matches("SUMAQ-[A-HJ-NP-Z2-9]{6}");
    }
}
