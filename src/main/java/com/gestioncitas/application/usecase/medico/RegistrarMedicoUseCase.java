package com.gestioncitas.application.usecase.medico;

import com.gestioncitas.domain.entity.Medico;
import com.gestioncitas.domain.repository.MedicoRepository;
import com.gestioncitas.domain.repository.EspecialidadRepository;
import java.time.LocalTime;

public class RegistrarMedicoUseCase {
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public RegistrarMedicoUseCase(MedicoRepository medicoRepository,
                                EspecialidadRepository especialidadRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
    }

    public Medico ejecutar(String nombre, String apellido, Long especialidadId,
                         LocalTime horarioInicio, LocalTime horarioFin) {
        // Validar especialidad
        var especialidad = especialidadRepository.buscarPorId(especialidadId)
            .orElseThrow(() -> new IllegalArgumentException("Especialidad no válida"));

        // Validar horario
        if (horarioInicio.isAfter(horarioFin)) {
            throw new IllegalArgumentException("El horario de inicio debe ser antes del fin");
        }

        Medico nuevoMedico = new Medico(
        );

        return medicoRepository.guardar(nuevoMedico);
    }
}