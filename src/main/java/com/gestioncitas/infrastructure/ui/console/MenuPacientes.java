package com.gestioncitas.infrastructure.ui.console;

import com.gestioncitas.application.usecase.paciente.RegistrarPacienteUseCase;
import java.util.Scanner;

public class MenuPacientes {
    private final RegistrarPacienteUseCase registrarPacienteUseCase;
    private final Scanner scanner;

    public MenuPacientes(RegistrarPacienteUseCase registrarPacienteUseCase, Scanner scanner) {
        this.registrarPacienteUseCase = registrarPacienteUseCase;
        this.scanner = scanner;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PACIENTES ===");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Buscar paciente");
            System.out.println("3. Listar todos los pacientes");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    buscarPaciente();
                    break;
                case 3:
                    listarPacientes();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private void registrarPaciente() {
        System.out.println("\n--- REGISTRAR NUEVO PACIENTE ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("Email (opcional): ");
        String email = scanner.nextLine();
        
        System.out.print("Dirección (opcional): ");
        String direccion = scanner.nextLine();

        try {
            var paciente = registrarPacienteUseCase.ejecutar(nombre, apellido, telefono, email, direccion);
            System.out.println("Paciente registrado con éxito. ID: " + paciente);
        } catch (Exception e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
        }
    }

    private void buscarPaciente() {
        System.out.println("\n--- BUSCAR PACIENTE ---");
        System.out.print("Ingrese ID o nombre del paciente: ");
        String criterio = scanner.nextLine();
        
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void listarPacientes() {
        System.out.println("\n--- LISTADO DE PACIENTES ---");
        System.out.println("Funcionalidad en desarrollo...");
    }
}