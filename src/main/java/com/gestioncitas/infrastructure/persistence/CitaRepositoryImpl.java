package com.gestioncitas.infrastructure.persistence;

import com.gestioncitas.domain.entity.Cita;
import com.gestioncitas.domain.entity.Medico;
import com.gestioncitas.domain.entity.Paciente;
import com.gestioncitas.domain.repository.CitaRepository;
import com.gestioncitas.domain.repository.MedicoRepository;
import com.gestioncitas.domain.repository.PacienteRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CitaRepositoryImpl implements CitaRepository {
    private final Connection connection;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.pacienteRepository = new PacienteRepositoryImpl(connection);
        this.medicoRepository = new MedicoRepositoryImpl(connection);
    }

    @Override
    public Cita guardar(Cita cita) {
        String sql = cita.getId() == null ?
            "INSERT INTO citas (paciente_id, medico_id, fecha_hora, estado) VALUES (?, ?, ?, ?)" :
            "UPDATE citas SET paciente_id = ?, medico_id = ?, fecha_hora = ?, estado = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, cita.getPaciente());
            stmt.setLong(2, cita.getMedico());
            stmt.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            stmt.setString(4, cita.getEstado());

            if (cita.getId() != null) {
                stmt.setLong(5, cita.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar la cita");
            }

            if (cita.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cita.setId(rs.getLong(1));
                    }
                }
            }
            return cita;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cita", e);
        }
    }

    @Override
    public boolean actualizar(Cita cita) {
        return guardar(cita) != null;
    }

    @Override
    public Optional<Cita> buscarPorId(Long id) {
        String sql = "SELECT * FROM citas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToCita(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cita por ID", e);
        }
    }

    @Override
    public List<Cita> buscarPorPaciente(Long pacienteId) {
        String sql = "SELECT * FROM citas WHERE paciente_id = ?";
        List<Cita> citas = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, pacienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapToCita(rs));
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar citas por paciente", e);
        }
    }

    @Override
    public List<Cita> buscarCompletadasPorPaciente(Long pacienteId) {
        String sql = "SELECT * FROM citas WHERE paciente_id = ? AND estado = 'COMPLETADA'";
        List<Cita> citas = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, pacienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapToCita(rs));
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar citas completadas por paciente", e);
        }
    }

    @Override
    public List<Cita> buscarPorMedicoYFecha(Long medicoId, LocalDateTime fecha) {
        String sql = "SELECT * FROM citas WHERE medico_id = ? AND DATE(fecha_hora) = DATE(?)";
        List<Cita> citas = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, medicoId);
            stmt.setTimestamp(2, Timestamp.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapToCita(rs));
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar citas por médico y fecha", e);
        }
    }

    @Override
    public boolean existeCitaEnHorario(Long medicoId, LocalDateTime fechaHora) {
        String sql = "SELECT COUNT(*) FROM citas WHERE medico_id = ? AND fecha_hora = ? AND estado != 'CANCELADA'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, medicoId);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHora));
            
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar disponibilidad", e);
        }
    }

    @Override
    public boolean cancelar(Long citaId) {
        String sql = "UPDATE citas SET estado = 'CANCELADA' WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, citaId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al cancelar cita", e);
        }
    }

    @Override
    public List<Cita> buscarPorEstado(String estado) {
        String sql = "SELECT * FROM citas WHERE estado = ?";
        List<Cita> citas = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapToCita(rs));
            }
            return citas;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar citas por estado", e);
        }
    }

    private Cita mapToCita(ResultSet rs) throws SQLException {
        Paciente paciente = pacienteRepository.buscarPorId(rs.getLong("paciente_id"))
            .orElseThrow(() -> new SQLException("Paciente no encontrado"));
        
        Medico medico = medicoRepository.buscarPorId(rs.getLong("medico_id"))
            .orElseThrow(() -> new SQLException("Médico no encontrado"));

        Cita cita = new Cita(
            rs.getLong("id"),
            paciente,
            medico,
            rs.getTimestamp("fecha_hora").toLocalDateTime()
        );
        
        // Establecer el estado de la cita
        cita.setEstado(rs.getString("estado"));
        return cita;
    }

    @Override
    public boolean existeCitaEnHorario(Medico medico, LocalDateTime nuevaFechaHora) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existeCitaEnHorario'");
    }
}