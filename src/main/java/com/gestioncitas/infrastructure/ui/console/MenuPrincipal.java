package com.gestioncitas.infrastructure.ui.console;

import com.gestioncitas.application.usecase.cita.AgendarCitaUseCase;
import com.gestioncitas.application.usecase.paciente.RegistrarPacienteUseCase;
import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner scanner;
    private final MenuPacientes menuPacientes;
    private final MenuCitas menuCitas;
    private final MenuMedicos menuMedicos;

    public MenuPrincipal(RegistrarPacienteUseCase registrarPacienteUseCase,
                        AgendarCitaUseCase agendarCitaUseCase) {
        this.scanner = new Scanner(System.in);
        this.menuPacientes = new MenuPacientes(registrarPacienteUseCase, scanner);
        this.menuCitas = new MenuCitas(agendarCitaUseCase, scanner);
        this.menuMedicos = new MenuMedicos(scanner);
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN CLÍNICA ===");
            System.out.println("1. Gestión de Pacientes");
            System.out.println("2. Gestión de Médicos");
            System.out.println("3. Gestión de Citas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    menuPacientes.mostrar();
                    break;
                case 2:
                    menuMedicos.mostrar();
                    break;
                case 3:
                    menuCitas.mostrar();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }
}