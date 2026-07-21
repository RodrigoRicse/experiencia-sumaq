package com.sumaq.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientePedidoDto(
        @NotBlank @Size(max = 100) String nombres,
        @NotBlank @Size(max = 100) String apellidos,
        @NotBlank @Pattern(regexp = "^[0-9+() -]{7,20}$") String telefono,
        @Email @Size(max = 150) String email
) {
}
