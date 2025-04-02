package com.gestioncitas.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Paciente {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;

    public Paciente(Long id, String nombre, String apellido, LocalDate fechaNacimiento, 
                   String direccion, String telefono, String email) {
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre, "Nombre no puede ser nulo");
        this.apellido = Objects.requireNonNull(apellido, "Apellido no puede ser nulo");
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = Objects.requireNonNull(telefono, "Teléfono no puede ser nulo");
        this.email = email;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = Objects.requireNonNull(nombre); }
    public void setApellido(String apellido) { this.apellido = Objects.requireNonNull(apellido); }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = Objects.requireNonNull(telefono); }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Paciente{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", fechaNacimiento=" + fechaNacimiento +
               ", direccion='" + direccion + '\'' +
               ", telefono='" + telefono + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}