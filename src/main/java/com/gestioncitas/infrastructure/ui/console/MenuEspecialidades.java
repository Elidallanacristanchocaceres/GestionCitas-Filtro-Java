package com.gestioncitas.infrastructure.ui.console;

import java.util.Scanner;

public class MenuEspecialidades {
    private final Scanner scanner;

    public MenuEspecialidades(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ ESPECIALIDADES ===");
            System.out.println("1. Listar todas las especialidades");
            System.out.println("2. Registrar nueva especialidad");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    listarEspecialidades();
                    break;
                case 2:
                    registrarEspecialidad();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);
    }

    private void listarEspecialidades() {
        System.out.println("\n--- LISTADO DE ESPECIALIDADES ---");
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void registrarEspecialidad() {
        System.out.println("\n--- REGISTRAR ESPECIALIDAD ---");
        System.out.print("Nombre de la especialidad: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.println("Funcionalidad en desarrollo...");
    }
}