package com.gestioncitas.domain.entity;

import java.time.LocalTime;
import java.util.Objects;

public class Medico {
    private Long id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;

    // Constructor
    public Medico(Long id, String nombre, String apellido, Especialidad especialidad, 
                 LocalTime horarioInicio, LocalTime horarioFin) {
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser nulo");
        this.apellido = Objects.requireNonNull(apellido, "Apellido no puede ser nulo");
        this.especialidad = Objects.requireNonNull(especialidad, "Especialidad no puede ser nula");
        this.horarioInicio = Objects.requireNonNull(horarioInicio, "Horario inicio no puede ser nulo");
        this.horarioFin = Objects.requireNonNull(horarioFin, "Horario fin no puede ser nulo");
        validarHorarios();
    }

    private void validarHorarios() {
        if (horarioInicio.isAfter(horarioFin)) {
            throw new IllegalArgumentException("El horario de inicio debe ser antes del horario de fin");
        }
    }

    // Getters
    public Long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Especialidad getEspecialidad() {
        return this.especialidad;
    }

    public LocalTime getHorarioInicio() {
        return this.horarioInicio;
    }

    public LocalTime getHorarioFin() {
        return this.horarioFin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser nulo");
    }

    public void setApellido(String apellido) {
        this.apellido = Objects.requireNonNull(apellido, "Apellido no puede ser nulo");
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = Objects.requireNonNull(especialidad, "Especialidad no puede ser nula");
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = Objects.requireNonNull(horarioInicio, "Horario inicio no puede ser nulo");
        validarHorarios();
    }

    public void setHorarioFin(LocalTime horarioFin) {
        this.horarioFin = Objects.requireNonNull(horarioFin, "Horario fin no puede ser nulo");
        validarHorarios();
    }

    @Override
    public String toString() {
        return "Medico [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido 
             + ", especialidad=" + especialidad + ", horarioInicio=" + horarioInicio 
             + ", horarioFin=" + horarioFin + "]";
    }
}