package com.gestioncitas.application.usecase.especialidad;

import com.gestioncitas.domain.entity.Especialidad;
import com.gestioncitas.domain.repository.EspecialidadRepository;
import java.util.List;

public class ListarEspecialidadesUseCase {
    private final EspecialidadRepository especialidadRepository;

    public ListarEspecialidadesUseCase(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public List<Especialidad> ejecutar() {
        return especialidadRepository.buscarTodas();
    }
}