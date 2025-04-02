package com.gestioncitas.infrastructure.persistence;

import com.gestioncitas.domain.entity.Medico;
import com.gestioncitas.domain.repository.EspecialidadRepository;
import com.gestioncitas.domain.repository.MedicoRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicoRepositoryImpl implements MedicoRepository {
    private final Connection connection;
    private final EspecialidadRepository especialidadRepository;

    public MedicoRepositoryImpl(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "La conexión no puede ser nula");
        this.especialidadRepository = new EspecialidadRepositoryImpl(connection);
    }

    @Override
    public Medico guardar(Medico medico) {
        final boolean esNuevo = medico.getId() == null;
        final String sql = esNuevo ?
            "INSERT INTO medicos (nombre, apellido, especialidad_id, horario_inicio, horario_fin) VALUES (?, ?, ?, ?, ?)" :
            "UPDATE medicos SET nombre = ?, apellido = ?, especialidad_id = ?, horario_inicio = ?, horario_fin = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql, 
             esNuevo ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getApellido());
            stmt.setLong(3, medico.getEspecialidad().getId());
            stmt.setTime(4, Time.valueOf(medico.getHorarioInicio()));
            stmt.setTime(5, Time.valueOf(medico.getHorarioFin()));

            if (!esNuevo) {
                stmt.setLong(6, medico.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar el médico, ninguna fila afectada");
            }

            if (esNuevo) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        medico.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("No se pudo obtener el ID generado para el médico");
                    }
                }
            }

            return medico;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el médico en la base de datos", e);
        }
    }

    @Override
    public Optional<Medico> buscarPorId(Long id) {
        final String sql = "SELECT * FROM medicos WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearAMedico(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar médico por ID: " + id, e);
        }
    }

    @Override
    public List<Medico> buscarPorEspecialidad(Long especialidadId) {
        final String sql = "SELECT * FROM medicos WHERE especialidad_id = ?";
        final List<Medico> medicos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, especialidadId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicos.add(mapearAMedico(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar médicos por especialidad: " + especialidadId, e);
        }
        
        return medicos;
    }

    @Override
    public List<Medico> buscarTodos() {
        final String sql = "SELECT * FROM medicos";
        final List<Medico> medicos = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                medicos.add(mapearAMedico(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los médicos", e);
        }
        
        return medicos;
    }

    @Override
    public boolean tieneCitasEntre(Long medicoId, LocalDateTime inicio, LocalDateTime fin) {
        final String sql = "SELECT COUNT(*) FROM citas WHERE medico_id = ? " +
                         "AND fecha_hora BETWEEN ? AND ? " +
                         "AND estado != 'CANCELADA'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, medicoId);
            stmt.setTimestamp(2, Timestamp.valueOf(inicio));
            stmt.setTimestamp(3, Timestamp.valueOf(fin));
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                String.format("Error al verificar citas para médico %d entre %s y %s", 
                medicoId, inicio, fin), e);
        }
    }

    private Medico mapearAMedico(ResultSet rs) throws SQLException {
        return new Medico(
            rs.getLong("id"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            especialidadRepository.buscarPorId(rs.getLong("especialidad_id"))
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada para médico")),
            rs.getTime("horario_inicio").toLocalTime(),
            rs.getTime("horario_fin").toLocalTime()
        );
    }
}