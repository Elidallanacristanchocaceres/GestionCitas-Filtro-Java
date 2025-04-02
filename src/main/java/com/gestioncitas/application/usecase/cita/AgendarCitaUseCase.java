package com.gestioncitas.application.usecase.cita;

import com.gestioncitas.domain.entity.Cita;
import com.gestioncitas.domain.repository.CitaRepository;
import com.gestioncitas.domain.repository.MedicoRepository;
import com.gestioncitas.domain.repository.PacienteRepository;
import java.time.LocalDateTime;

public class AgendarCitaUseCase {
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public AgendarCitaUseCase(CitaRepository citaRepository, 
                            PacienteRepository pacienteRepository,
                            MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public Cita ejecutar(Long pacienteId, Long medicoId, LocalDateTime fechaHora) {
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se pueden agendar citas en el pasado");
        }

        var paciente = pacienteRepository.buscarPorId(pacienteId)
            .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        
        var medico = medicoRepository.buscarPorId(medicoId)
            .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        if (citaRepository.existeCitaEnHorario(medicoId, fechaHora)) {
            throw new IllegalStateException("El médico ya tiene una cita programada en ese horario");
        }

        Cita nuevaCita = new Cita(null, paciente, medico, fechaHora);
        return citaRepository.guardar(nuevaCita);
    }
}