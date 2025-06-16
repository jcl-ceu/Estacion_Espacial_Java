package com.estacionespacial.model.tripulacion;

public class Comandante extends Tripulante {
    private String rango;

    public Comandante(String id, String nombre, String rango, double horas) {
        super(id, nombre, "Comandante");
        this.rango = rango;
        trabajar(horas);
    }
    public Comandante(String id, String nombre, String rango) {
        this(id, nombre, rango, 0);
    }

    public String getRango() { return rango; }
    public void setRango(String rango) {
        if (rango == null || rango.isBlank()) throw new IllegalArgumentException("Rango inválido");
        this.rango = rango;
    }

    @Override
    public String getInfoCompleta() {
        return String.format("ID:%s, Nombre:%s, Rol:%s, Rango:%s, Horas:%.2f",
            getId(), getNombre(), getRol(), rango, getHorasTrabajo());
    }

    @Override
    public String toCSV() {
        return super.toCSV() + ";" + rango;
    }
}
