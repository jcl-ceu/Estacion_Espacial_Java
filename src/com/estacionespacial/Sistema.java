package com.estacionespacial;

import com.estacionespacial.exceptions.*;
import com.estacionespacial.model.tripulacion.*;
import com.estacionespacial.model.modulo.*;
import com.estacionespacial.model.mision.Mision;
import com.estacionespacial.model.recurso.RecursoVital;
import com.estacionespacial.persistence.CSVHandler;
import com.estacionespacial.utils.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Sistema {
    private List<Tripulante> tripulantes = new ArrayList<>();
    private List<Modulo>     modulos      = new ArrayList<>();
    private List<Mision>     misiones     = new ArrayList<>();
    private List<RecursoVital> recursos    = new ArrayList<>();

    private static final String PATH_TRIP = "tripulantes.csv";
    private static final String PATH_MOD  = "modulos.csv";
    private static final String PATH_MIS  = "misiones.csv";
    private static final String PATH_REC  = "recursos.csv";

    /** Carga todos los datos desde CSV al iniciar */
    public void cargarDatos() {
        try {
            // Tripulantes
            for (var arr : CSVHandler.leer(PATH_TRIP)) {
                String id   = arr[0];
                String nom  = arr[1];
                String rol  = arr[2];
                double hrs  = Double.parseDouble(arr[3]);
                Tripulante t;
                switch (rol) {
                    case "Comandante":
                        t = new Comandante(id, nom, arr[4], hrs);
                        break;
                    case "Ingeniero":
                        t = new Ingeniero(id, nom, arr[4], hrs);
                        break;
                    case "Médico":
                        t = new Medico(id, nom, Integer.parseInt(arr[4]), hrs);
                        break;
                    default:
                        continue;
                }
                tripulantes.add(t);
            }

            // Módulos
            for (var arr : CSVHandler.leer(PATH_MOD)) {
                String tipo = arr[2];
                Modulo m;
                switch (tipo) {
                    case "Habitacional":
                        m = new ModuloHabitacional(arr[0], arr[1],
                                Integer.parseInt(arr[3]),
                                Boolean.parseBoolean(arr[4]),
                                Integer.parseInt(arr[5]));
                        break;
                    case "Investigación":
                        m = new ModuloInvestigacion(arr[0], arr[1],
                                Integer.parseInt(arr[3]),
                                Boolean.parseBoolean(arr[4]),
                                arr[5]);
                        break;
                    case "Mando":
                        m = new ModuloMando(arr[0], arr[1],
                                Integer.parseInt(arr[3]),
                                Boolean.parseBoolean(arr[4]),
                                Boolean.parseBoolean(arr[5]));
                        break;
                    default:
                        continue;
                }
                modulos.add(m);
            }

            // Recursos
            for (var arr : CSVHandler.leer(PATH_REC)) {
                recursos.add(new RecursoVital(
                        arr[0],
                        Double.parseDouble(arr[1]),
                        Double.parseDouble(arr[2])
                ));
            }

            // Misiones
            for (var arr : CSVHandler.leer(PATH_MIS)) {
                Mision m = new Mision(
                        arr[0],
                        arr[1],
                        LocalDate.parse(arr[2]),
                        LocalDate.parse(arr[3]),
                        Boolean.parseBoolean(arr[4])
                );
                // IDs de participantes separadas por coma
                if (!arr[5].isBlank()) {
                    for (String pid : arr[5].split(",")) {
                        tripulantes.stream()
                            .filter(t -> t.getId().equals(pid))
                            .findFirst()
                            .ifPresent(m::añadirTripulante);
                    }
                }
                misiones.add(m);
            }

        } catch (IOException e) {
            System.out.println("Error cargando datos: " + e.getMessage());
        }
    }

    /** Guarda todos los datos a CSV al salir */
    public void guardarDatos() {
        try {
            // Tripulantes
            List<String[]> outT = new ArrayList<>();
            for (var t : tripulantes) {
                outT.add(t.toCSV().split(";"));
            }
            CSVHandler.escribir(PATH_TRIP, outT);

            // Módulos
            List<String[]> outM = new ArrayList<>();
            for (var m : modulos) {
                outM.add(m.toCSV().split(";"));
            }
            CSVHandler.escribir(PATH_MOD, outM);

            // Recursos
            List<String[]> outR = new ArrayList<>();
            for (var r : recursos) {
                outR.add(r.toCSV().split(";"));
            }
            CSVHandler.escribir(PATH_REC, outR);

            // Misiones
            List<String[]> outMi = new ArrayList<>();
            for (var mi : misiones) {
                outMi.add(mi.toCSV().split(";"));
            }
            CSVHandler.escribir(PATH_MIS, outMi);

        } catch (IOException e) {
            System.out.println("Error guardando datos: " + e.getMessage());
        }
    }

    /** Menú principal */
    public void mostrarMenu() {
        int opt;
        do {
            System.out.println("\n=== SISTEMA ESTACIÓN ESPACIAL ===");
            System.out.println("1. Gestionar tripulación");
            System.out.println("2. Gestionar módulos");
            System.out.println("3. Gestionar misiones");
            System.out.println("4. Controlar recursos");
            System.out.println("5. Generar informes");
            System.out.println("6. Guardar y salir");
            opt = Utils.leerEntero("Seleccione opción: ");
            switch (opt) {
                case 1 -> menuTripulacion();
                case 2 -> menuModulos();
                case 3 -> menuMisiones();
                case 4 -> menuRecursos();
                case 5 -> generarInformes();
                case 6 -> System.out.println("Guardando y saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opt != 6);
    }

    //--- Tripulación CRUD ---------------------------------------------------

    private void menuTripulacion() {
        int o;
        do {
            System.out.println("\n--- Tripulación ---");
            System.out.println("1. Añadir");
            System.out.println("2. Listar");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            o = Utils.leerEntero("Opción: ");
            switch (o) {
                case 1 -> añadirTripulante();
                case 2 -> listarTripulantes();
                case 3 -> editarTripulante();
                case 4 -> eliminarTripulante();
            }
        } while (o != 5);
    }

    private void añadirTripulante() {
        try {
            String tipo = Utils.leerCadena("Tipo (C=Comandante, I=Ingeniero, M=Médico): ");
            String id   = Utils.generarUUID();
            String nombre = Utils.leerCadena("Nombre: ");

            switch (tipo.toUpperCase()) {
                case "C" -> {
                    String rango = Utils.leerCadena("Rango: ");
                    tripulantes.add(new Comandante(id, nombre, rango));
                }
                case "I" -> {
                    String esp = Utils.leerCadena("Especialidad: ");
                    tripulantes.add(new Ingeniero(id, nombre, esp));
                }
                case "M" -> {
                    int pac = Utils.leerEntero("Pacientes atendidos: ");
                    tripulantes.add(new Medico(id, nombre, pac));
                }
                default -> {
                    System.out.println("Tipo inválido");
                    return;
                }
            }
            System.out.println("Tripulante añadido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        Utils.pausa();
    }

    private void listarTripulantes() {
        tripulantes.forEach(t -> System.out.println(t.getInfoCompleta()));
        Utils.pausa();
    }

    private void editarTripulante() {
        String id = Utils.leerCadena("ID a editar: ");
        tripulantes.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .ifPresentOrElse(t -> {
                String nom = Utils.leerCadena("Nuevo nombre: ");
                t.setNombre(nom);
                System.out.println("Actualizado.");
            }, () -> System.out.println("No encontrado."));
        Utils.pausa();
    }

    private void eliminarTripulante() {
        String id = Utils.leerCadena("ID a eliminar: ");
        if (tripulantes.removeIf(t -> t.getId().equals(id)))
            System.out.println("Eliminado.");
        else
            System.out.println("No encontrado.");
        Utils.pausa();
    }

    //--- Módulos CRUD --------------------------------------------------------

    private void menuModulos() {
        int o;
        do {
            System.out.println("\n--- Módulos ---");
            System.out.println("1. Añadir");
            System.out.println("2. Listar");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("5. Volver");
            o = Utils.leerEntero("Opción: ");
            switch (o) {
                case 1 -> añadirModulo();
                case 2 -> listarModulos();
                case 3 -> editarModulo();
                case 4 -> eliminarModulo();
            }
        } while (o != 5);
    }

    private void añadirModulo() {
        try {
            String tipo = Utils.leerCadena("Tipo (H=Habitacional, I=Investigación, M=Mando): ");
            String cod  = Utils.generarUUID();
            String nom  = Utils.leerCadena("Nombre: ");
            int cap     = Utils.leerEntero("Capacidad: ");
            boolean op  = Utils.leerCadena("Operativo (s/n): ").equalsIgnoreCase("s");

            switch (tipo.toUpperCase()) {
                case "H" -> {
                    int cam = Utils.leerEntero("Camas: ");
                    modulos.add(new ModuloHabitacional(cod, nom, cap, op, cam));
                }
                case "I" -> {
                    String campo = Utils.leerCadena("Campo investigación: ");
                    modulos.add(new ModuloInvestigacion(cod, nom, cap, op, campo));
                }
                case "M" -> {
                    boolean ia = Utils.leerCadena("IA (s/n): ").equalsIgnoreCase("s");
                    modulos.add(new ModuloMando(cod, nom, cap, op, ia));
                }
                default -> {
                    System.out.println("Tipo inválido");
                    return;
                }
            }
            System.out.println("Módulo añadido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        Utils.pausa();
    }

    private void listarModulos() {
        modulos.forEach(m -> System.out.println(m.toCSV()));
        Utils.pausa();
    }

    private void editarModulo() {
        String cod = Utils.leerCadena("Código a editar: ");
        modulos.stream()
            .filter(m -> m.getCodigo().equals(cod))
            .findFirst()
            .ifPresentOrElse(m -> {
                String nom = Utils.leerCadena("Nuevo nombre: ");
                m.setNombre(nom);
                System.out.println("Actualizado.");
            }, () -> System.out.println("No encontrado."));
        Utils.pausa();
    }

    private void eliminarModulo() {
        String cod = Utils.leerCadena("Código a eliminar: ");
        if (modulos.removeIf(m -> m.getCodigo().equals(cod)))
            System.out.println("Eliminado.");
        else
            System.out.println("No encontrado.");
        Utils.pausa();
    }

    //--- Misiones CRUD + Asignaciones --------------------------------------

    private void menuMisiones() {
        int o;
        do {
            System.out.println("\n--- Misiones ---");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Asignar Tripulante");
            System.out.println("4. Finalizar");
            System.out.println("5. Volver");
            o = Utils.leerEntero("Opción: ");
            switch (o) {
                case 1 -> crearMision();
                case 2 -> listarMisiones();
                case 3 -> asignarTripulanteMision();
                case 4 -> finalizarMision();
            }
        } while (o != 5);
    }

    private void crearMision() {
        String desc = Utils.leerCadena("Descripción: ");
        LocalDate ini = Utils.leerFecha("Fecha inicio");
        LocalDate fin = Utils.leerFecha("Fecha fin");
        misiones.add(new Mision(desc, ini, fin));
        System.out.println("Misión creada.");
        Utils.pausa();
    }

    private void listarMisiones() {
        misiones.forEach(m -> System.out.println(m.getInfo()));
        Utils.pausa();
    }

    private void asignarTripulanteMision() {
        String mid = Utils.leerCadena("ID misión: ");
        String tid = Utils.leerCadena("ID tripulante: ");
        Optional<Mision> om = misiones.stream()
            .filter(m -> m.getIdMision().equals(mid))
            .findFirst();
        Optional<Tripulante> ot = tripulantes.stream()
            .filter(t -> t.getId().equals(tid))
            .findFirst();

        if (om.isPresent() && ot.isPresent()) {
            om.get().añadirTripulante(ot.get());
            System.out.println("Asignado.");
        } else {
            System.out.println("Misión o tripulante no encontrado.");
        }
        Utils.pausa();
    }

    private void finalizarMision() {
        String mid = Utils.leerCadena("ID misión: ");
        misiones.stream()
            .filter(m -> m.getIdMision().equals(mid))
            .findFirst()
            .ifPresent(m -> {
                m.marcarCompletada();
                System.out.println("Finalizada.");
            });
        Utils.pausa();
    }

    //--- Recursos: Consumir y Reabastecer ----------------------------------

    private void menuRecursos() {
        int o;
        do {
            System.out.println("\n--- Recursos ---");
            System.out.println("1. Consumir");
            System.out.println("2. Reabastecer");
            System.out.println("3. Listar");
            System.out.println("4. Volver");
            o = Utils.leerEntero("Opción: ");
            switch (o) {
                case 1 -> consumirRecurso();
                case 2 -> reabastecerRecurso();
                case 3 -> listarRecursos();
            }
        } while (o != 4);
    }

    private void consumirRecurso() {
        try {
            String tipo = Utils.leerCadena("Tipo: ");
            double cant = Utils.leerDouble("Cantidad: ");
            recursos.stream()
                .filter(r -> r.getTipo().equalsIgnoreCase(tipo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe"))
                .consumir(cant);
            System.out.println("Consumido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        Utils.pausa();
    }

    private void reabastecerRecurso() {
        String tipo = Utils.leerCadena("Tipo: ");
        double cant = Utils.leerDouble("Cantidad: ");
        recursos.stream()
            .filter(r -> r.getTipo().equalsIgnoreCase(tipo))
            .findFirst()
            .ifPresent(r -> {
                r.reabastecer(cant);
                System.out.println("Reabastecido.");
            });
        Utils.pausa();
    }

    private void listarRecursos() {
        recursos.forEach(r -> System.out.println(r.getInfo()));
        Utils.pausa();
    }

    //--- Informes Dinámicos ------------------------------------------------

    private void generarInformes() {
        System.out.println("\n--- INFORMES ---");

        // Tripulantes por rol
        System.out.println("Tripulantes por rol:");
        Map<String, Long> byRol = new HashMap<>();
        tripulantes.forEach(t ->
            byRol.merge(t.getRol(), 1L, Long::sum)
        );
        byRol.forEach((rol, cnt) ->
            System.out.println(rol + ": " + cnt)
        );

        // Estado de módulos
        System.out.println("\nEstado de módulos:");
        modulos.forEach(m ->
            System.out.printf("%s [%s] - %s\n",
                m.getCodigo(),
                m.getTipo(),
                m.isOperativo() ? "Operativo" : "Inoperativo")
        );

        // Misiones
        System.out.println("\nMisiones en curso:");
        misiones.stream()
            .filter(m -> !m.isFinalizada())
            .forEach(m -> System.out.println(m.getInfo()));

        System.out.println("\nMisiones finalizadas:");
        misiones.stream()
            .filter(m -> m.isFinalizada())
            .forEach(m -> System.out.println(m.getInfo()));

        // Consumo acumulado de recursos (estimado)
        System.out.println("\nConsumo estimado recursos:");
        misiones.size(); // no usado, solo para ejemplo
        recursos.forEach(r ->
            System.out.printf("%s: %.2f total (%.2f/día)\n",
                r.getTipo(),
                r.getConsumoDiario() * misiones.size(),
                r.getConsumoDiario())
        );

        // Alertas por bajo stock
        System.out.println("\nAlertas bajo stock:");
        recursos.stream()
            .filter(RecursoVital::alertaBajoStock)
            .forEach(r -> System.out.println(r.getTipo()));

        Utils.pausa();
    }
}
