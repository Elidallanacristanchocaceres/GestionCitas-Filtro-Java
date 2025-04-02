package com.gestioncitas.infrastructure.ui.console;

import com.gestioncitas.application.usecase.cita.AgendarCitaUseCase;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuCitas {
    private final AgendarCitaUseCase agendarCitaUseCase;
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MenuCitas(AgendarCitaUseCase agendarCitaUseCase, Scanner scanner) {
        this.agendarCitaUseCase = agendarCitaUseCase;
        this.scanner = scanner;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ CITAS ===");
            System.out.println("1. Agendar nueva cita");
            System.out.println("2. Cancelar cita");
            System.out.println("3. Consultar citas por paciente");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    agendarCita();
                    break;
                case 2:
                    cancelarCita();
                    break;
                case 3:
                    consultarCitasPaciente();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private void agendarCita() {
        System.out.println("\n--- AGENDAR NUEVA CITA ---");
        
        System.out.print("ID del paciente: ");
        Long pacienteId = scanner.nextLong();
        
        System.out.print("ID del médico: ");
        Long medicoId = scanner.nextLong();
        
        scanner.nextLine(); 
        System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
        String fechaHoraStr = scanner.nextLine();
        
        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);
            var cita = agendarCitaUseCase.ejecutar(pacienteId, medicoId, fechaHora);
            System.out.println("Cita agendada con éxito. ID: " + cita.getId());
        } catch (Exception e) {
            System.err.println("Error al agendar cita: " + e.getMessage());
        }
    }

    private void cancelarCita() {
        System.out.println("\n--- CANCELAR CITA ---");
        System.out.print("Ingrese el ID de la cita a cancelar: ");
        Long citaId = scanner.nextLong();
        scanner.nextLine(); 
        
        System.out.println("Funcionalidad de cancelación en desarrollo...");
    }

    private void consultarCitasPaciente() {
        System.out.println("\n--- CITAS POR PACIENTE ---");
        System.out.print("Ingrese ID del paciente: ");
        Long pacienteId = scanner.nextLong();
        scanner.nextLine(); 
        
        System.out.println("Funcionalidad de consulta en desarrollo...");
    }
}