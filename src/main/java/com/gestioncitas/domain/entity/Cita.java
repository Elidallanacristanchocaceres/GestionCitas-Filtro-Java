package com.gestioncitas.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Cita {
    private Long id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String estado;

    public Cita(Long id, Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = Objects.requireNonNull(paciente);
        this.medico = Objects.requireNonNull(medico);
        this.fechaHora = Objects.requireNonNull(fechaHora);
        this.estado = "PROGRAMADA";
        validarFechaHora();
    }

    private void validarFechaHora() {
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se pueden crear citas en el pasado");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { 
        this.fechaHora = fechaHora;
        validarFechaHora();
    }
    public String getEstado() { return estado; }

    public void cancelar() {
        if (!"PROGRAMADA".equals(estado)) {
            throw new IllegalStateException("Solo se pueden cancelar citas programadas");
        }
        this.estado = "CANCELADA";
    }

    public void completar() {
        if (!"PROGRAMADA".equals(estado)) {
            throw new IllegalStateException("Solo se pueden completar citas programadas");
        }
        this.estado = "COMPLETADA";
    }

    public void setEstado(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }
}