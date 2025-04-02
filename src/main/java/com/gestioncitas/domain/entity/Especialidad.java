package com.gestioncitas.domain.entity;

import java.util.Objects;

public class Especialidad {
    private Long id;
    private String nombre;
    private String descripcion;

    public Especialidad(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre);
        this.descripcion = descripcion;
        validarNombre();
    }

    private void validarNombre() {
        if (nombre.length() < 3 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 3 y 50 caracteres");
        }
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    // Método añadido para solucionar el error
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre);
        validarNombre();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}