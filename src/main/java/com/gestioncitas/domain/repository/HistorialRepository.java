package com.gestioncitas.domain.repository;

import com.gestioncitas.domain.entity.Historial;
import java.util.Optional;

public interface HistorialRepository {
    
    Historial guardar(Historial historial);
    Optional<Historial> buscarPorPaciente(Long pacienteId);
    boolean agregarRegistro(Long pacienteId, String observaciones, String tratamiento);
    String generarReportePdf(Long pacienteId);
}