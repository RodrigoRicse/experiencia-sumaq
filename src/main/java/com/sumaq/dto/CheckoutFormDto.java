package com.sumaq.dto;

import com.sumaq.model.MetodoPago;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CheckoutFormDto {

    @NotBlank(message = "Ingresa tus nombres")
    @Size(max = 100, message = "Los nombres no pueden superar 100 caracteres")
    private String nombres;

    @NotBlank(message = "Ingresa tus apellidos")
    @Size(max = 100, message = "Los apellidos no pueden superar 100 caracteres")
    private String apellidos;

    @NotBlank(message = "Ingresa un teléfono")
    @Pattern(regexp = "^[0-9+() -]{7,20}$", message = "Ingresa un teléfono válido")
    private String telefono;

    @Email(message = "Ingresa un correo válido")
    @Size(max = 150, message = "El correo no puede superar 150 caracteres")
    private String email;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;

    @NotNull(message = "Selecciona un método de pago")
    private MetodoPago metodoPago = MetodoPago.YAPE;

    @NotNull(message = "Selecciona el resultado de la simulación")
    private Boolean pagoAprobado = Boolean.TRUE;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Boolean getPagoAprobado() {
        return pagoAprobado;
    }

    public void setPagoAprobado(Boolean pagoAprobado) {
        this.pagoAprobado = pagoAprobado;
    }
}
