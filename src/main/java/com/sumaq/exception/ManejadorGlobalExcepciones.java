package com.sumaq.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ControllerAdvice
public class ManejadorGlobalExcepciones {

    private static final Logger LOG = LoggerFactory.getLogger(ManejadorGlobalExcepciones.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String recursoNoEncontrado(RecursoNoEncontradoException exception, Model model) {
        model.addAttribute("titulo", "No encontramos lo que buscabas");
        model.addAttribute("mensaje", exception.getMessage());
        return "error/estado";
    }

    @ExceptionHandler({ProductoNoDisponibleException.class, TransicionEstadoInvalidaException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String conflictoDeNegocio(RuntimeException exception, Model model) {
        model.addAttribute("titulo", "No pudimos completar la operación");
        model.addAttribute("mensaje", exception.getMessage());
        return "error/estado";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String entradaInvalida(ConstraintViolationException exception, Model model) {
        model.addAttribute("titulo", "Revisa los datos enviados");
        model.addAttribute("mensaje", "Uno o más valores no cumplen las reglas permitidas.");
        return "error/estado";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String errorNoControlado(Exception exception, Model model) {
        String codigoError = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LOG.error("Error web no controlado, referencia={}", codigoError, exception);
        model.addAttribute("titulo", "Ocurrió un problema inesperado");
        model.addAttribute("mensaje", "Inténtalo nuevamente. Si continúa, informa la referencia " + codigoError + ".");
        return "error/estado";
    }
}
