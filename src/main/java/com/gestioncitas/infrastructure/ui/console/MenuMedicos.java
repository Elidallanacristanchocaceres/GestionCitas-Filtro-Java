package com.gestioncitas.infrastructure.ui.console;

import java.util.Scanner;

public class MenuMedicos {
    private final Scanner scanner;

    public MenuMedicos(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ MÉDICOS ===");
            System.out.println("1. Registrar nuevo médico");
            System.out.println("2. Buscar médico");
            System.out.println("3. Listar médicos por especialidad");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    registrarMedico();
                    break;
                case 2:
                    buscarMedico();
                    break;
                case 3:
                    listarPorEspecialidad();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private void registrarMedico() {
        System.out.println("\n--- REGISTRAR NUEVO MÉDICO ---");
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void buscarMedico() {
        System.out.println("\n--- BUSCAR MÉDICO ---");
        System.out.print("Ingrese ID o nombre del médico: ");
        String criterio = scanner.nextLine();
        
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void listarPorEspecialidad() {
        System.out.println("\n--- MÉDICOS POR ESPECIALIDAD ---");
        System.out.println("Funcionalidad en desarrollo...");
    }
}