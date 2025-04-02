package com.gestioncitas.application.usecase.cita;

import com.gestioncitas.domain.entity.Cita;
import com.gestioncitas.domain.repository.CitaRepository;

public class CancelarCitaUseCase {
    private final CitaRepository citaRepository;

    public CancelarCitaUseCase(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public boolean ejecutar(Long citaId) {
        var cita = citaRepository.buscarPorId(citaId)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        
        cita.cancelar();
        
        return citaRepository.actualizar(cita);
    }
}