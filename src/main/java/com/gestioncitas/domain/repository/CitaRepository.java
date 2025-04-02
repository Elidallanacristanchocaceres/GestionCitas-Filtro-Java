package com.gestioncitas.domain.repository;

import com.gestioncitas.domain.entity.Cita;
import com.gestioncitas.domain.entity.Medico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository {
    Cita guardar(Cita cita);
    Optional<Cita> buscarPorId(Long id);
    List<Cita> buscarPorPaciente(Long pacienteId);
    List<Cita> buscarPorMedicoYFecha(Long medicoId, LocalDateTime fecha);
    boolean existeCitaEnHorario(Long medicoId, LocalDateTime fechaHora);
    boolean cancelar(Long citaId);
    List<Cita> buscarPorEstado(String estado);
    boolean actualizar(Cita cita);
    List<Cita> buscarCompletadasPorPaciente(Long pacienteId);
    boolean existeCitaEnHorario(Medico medico, LocalDateTime nuevaFechaHora);
}