package com.gestioncitas.application.usecase.historial;

import com.gestioncitas.domain.entity.Cita;
import com.gestioncitas.domain.repository.CitaRepository;
import java.util.List;

public class ConsultarHistorialUseCase {
    private final CitaRepository citaRepository;

    public ConsultarHistorialUseCase(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> porPaciente(Long pacienteId) {
        return citaRepository.buscarPorPaciente(pacienteId);
    }

    public List<Cita> completadasPorPaciente(Long pacienteId) {
        return citaRepository.buscarCompletadasPorPaciente(pacienteId);
    }
}