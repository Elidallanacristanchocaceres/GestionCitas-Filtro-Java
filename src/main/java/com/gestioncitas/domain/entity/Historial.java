package com.gestioncitas.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Historial {
    private Long id;
    private Paciente paciente;
    private final List<RegistroHistorial> registros;

    public Historial(Long id, Paciente paciente) {
        this.id = id;
        this.paciente = Objects.requireNonNull(paciente);
        this.registros = new ArrayList<>();
    }

    public Long getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public List<RegistroHistorial> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    public void agregarRegistro(String observaciones, String tratamiento) {
        RegistroHistorial nuevoRegistro = new RegistroHistorial(
            LocalDateTime.now(),
            observaciones,
            tratamiento
        );
        registros.add(nuevoRegistro);
    }

    public static class RegistroHistorial {
        private final LocalDateTime fecha;
        private final String observaciones;
        private final String tratamiento;

        public RegistroHistorial(LocalDateTime fecha, String observaciones, String tratamiento) {
            this.fecha = Objects.requireNonNull(fecha);
            this.observaciones = Objects.requireNonNull(observaciones);
            this.tratamiento = Objects.requireNonNull(tratamiento);
        }

        public LocalDateTime getFecha() { return fecha; }
        public String getObservaciones() { return observaciones; }
        public String getTratamiento() { return tratamiento; }
    }

    public void setId(long long1) {
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}