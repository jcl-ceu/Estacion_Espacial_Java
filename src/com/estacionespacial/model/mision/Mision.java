package com.estacionespacial.model.mision;

import com.estacionespacial.model.tripulacion.Tripulante;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Mision {
    private String idMision, descripcion;
    private LocalDate fechaInicio, fechaFin;
    private List<Tripulante> participantes;
    private boolean finalizada;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    public Mision(String idMision, String descripcion,
                  LocalDate inicio, LocalDate fin, boolean finalizada) {
        this.idMision = idMision;
        this.descripcion = descripcion;
        this.fechaInicio = inicio;
        this.fechaFin = fin;
        this.participantes = new ArrayList<>();
        this.finalizada = finalizada;
    }
    public Mision(String descripcion, LocalDate inicio, LocalDate fin) {
        this(UUID.randomUUID().toString(), descripcion, inicio, fin, false);
    }

    public String getIdMision() {
        return idMision;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void añadirTripulante(Tripulante t) {
        participantes.add(t);
    }
    public void marcarCompletada() {
        finalizada = true;
    }
    public String getEstado() {
        return finalizada ? "Finalizada" : "En curso";
    }
    public long getDuracionDias() {
        return ChronoUnit.DAYS.between(fechaInicio, LocalDate.now());
    }
    public String getInfo() {
        return String.format("Misión %s: %s (Estado: %s, Duración: %d días)",
            idMision, descripcion, getEstado(), getDuracionDias());
    }

    public String toCSV() {
        String base = String.join(";", idMision, descripcion,
            fechaInicio.format(FMT), fechaFin.format(FMT),
            String.valueOf(finalizada));
        String partIds = String.join(",",
            participantes.stream().map(Tripulante::getId).toArray(String[]::new));
        return base + ";" + partIds;
    }
    public static String[] fromCSV(String line) {
        return line.split(";");
    }
}
