package com.gestioncitas.application.usecase.cita;

import com.gestioncitas.domain.entity.Cita;
import com.gestioncitas.domain.repository.CitaRepository;
import com.gestioncitas.domain.repository.MedicoRepository;
import java.time.LocalDateTime;

public class ModificarCitaUseCase {
    private final CitaRepository citaRepository;
    private final MedicoRepository medicoRepository;

    public ModificarCitaUseCase(CitaRepository citaRepository, 
                              MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.medicoRepository = medicoRepository;
    }

    public Cita ejecutar(Long citaId, Long nuevoMedicoId, LocalDateTime nuevaFechaHora) {
        var cita = citaRepository.buscarPorId(citaId)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        if ("CANCELADA".equals(cita.getEstado())) {
            throw new IllegalStateException("No se puede modificar una cita cancelada");
        }

        if (!cita.getMedico().equals(nuevoMedicoId)) {
            var nuevoMedico = medicoRepository.buscarPorId(nuevoMedicoId)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));
            cita.setMedico(nuevoMedico);
        }

        if (!cita.getFechaHora().equals(nuevaFechaHora)) {
            if (citaRepository.existeCitaEnHorario(cita.getMedico(), nuevaFechaHora)) {
                throw new IllegalStateException("El médico ya tiene una cita en el nuevo horario");
            }
            cita.setFechaHora(nuevaFechaHora);
        }

        return citaRepository.guardar(cita);
    }
}