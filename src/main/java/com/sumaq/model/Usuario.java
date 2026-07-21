package com.sumaq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @NotBlank
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    protected Usuario() {
    }

    public Usuario(Rol rol, String username, String passwordHash, String nombres, String apellidos) {
        this.rol = rol;
        this.username = username;
        this.passwordHash = passwordHash;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    @PrePersist
    void antesDeCrear() {
        creadoEn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void cambiarActivo(boolean activo) {
        this.activo = activo;
    }
}
