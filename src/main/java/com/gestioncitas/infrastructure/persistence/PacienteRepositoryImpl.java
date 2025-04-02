package com.gestioncitas.infrastructure.persistence;

import com.gestioncitas.domain.entity.Paciente;
import com.gestioncitas.domain.repository.PacienteRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteRepositoryImpl implements PacienteRepository {

    @Override
    public boolean existePorTelefono(String telefono) {
        String sql = "SELECT COUNT(*) FROM pacientes WHERE telefono = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, telefono);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar si existe un paciente por teléfono", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo eliminar el paciente con ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar paciente", e);
        }
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM pacientes WHERE nombre LIKE ?";
        List<Paciente> pacientes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalDate fechaNacimiento = null;
                if (rs.getDate("fecha_nacimiento") != null) {
                    fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
                }
                pacientes.add(new Paciente(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    fechaNacimiento,
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email")
                ));
            }
            return pacientes;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pacientes por nombre", e);
        }
    }
    private final Connection connection;

    public PacienteRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        String sql = paciente.getId() == null ?
            "INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?)" :
            "UPDATE pacientes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, paciente.getFechaNacimiento() != null ? 
                Date.valueOf(paciente.getFechaNacimiento()) : null);
            stmt.setString(4, paciente.getDireccion());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getEmail());

            if (paciente.getId() != null) {
                stmt.setLong(7, paciente.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar el paciente");
            }

            if (paciente.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        paciente.setId(rs.getLong(1));
                    }
                }
            }
            return paciente;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar paciente", e);
        }
    }

@Override
public Optional<Paciente> buscarPorId(Long id) {
    String sql = "SELECT * FROM pacientes WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Date fechaNacimientoSQL = rs.getDate("fecha_nacimiento");
            LocalDate fechaNacimiento = (fechaNacimientoSQL != null) ? 
                fechaNacimientoSQL.toLocalDate() : null;
            
            String direccion = rs.getString("direccion");
            String email = rs.getString("email");

            return Optional.of(new Paciente(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                fechaNacimiento,
                direccion,
                rs.getString("telefono"),
                email
            ));
        }
        return Optional.empty();
    } catch (SQLException e) {
        throw new RuntimeException("Error al buscar paciente por ID", e);
    }
}

    @Override
    public List<Paciente> buscarTodos() {
        String sql = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                LocalDate fechaNacimiento = null;
                if (rs.getDate("fecha_nacimiento") != null) {
                    fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
                }
                
                pacientes.add(new Paciente(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    fechaNacimiento,
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email")
                ));
            }
            return pacientes;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los pacientes", e);
        }
    }

}