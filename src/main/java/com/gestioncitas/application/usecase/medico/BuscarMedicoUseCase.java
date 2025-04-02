package com.gestioncitas.application.usecase.medico;

import com.gestioncitas.domain.entity.Medico;
import com.gestioncitas.domain.repository.MedicoRepository;
import java.util.List;
import java.util.Optional;

public class BuscarMedicoUseCase {
    private final MedicoRepository medicoRepository;

    public BuscarMedicoUseCase(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Optional<Medico> porId(Long id) {
        return medicoRepository.buscarPorId(id);
    }

    public List<Medico> porEspecialidad(Long especialidadId) {
        return medicoRepository.buscarPorEspecialidad(especialidadId);
    }

    public List<Medico> todos() {
        return medicoRepository.buscarTodos();
    }
}