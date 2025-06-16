package com.estacionespacial.model.modulo;

public abstract class Modulo {
    private String codigo, nombre;
    private int capacidad;
    private boolean operativo;

    public Modulo(String codigo, String nombre, int capacidad, boolean operativo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.operativo = operativo;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public boolean isOperativo() { return operativo; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre;
    }
    public void setCapacidad(int c) {
        if (c < 0) throw new IllegalArgumentException("Capacidad inválida");
        this.capacidad = c;
    }
    public void setOperativo(boolean op) { this.operativo = op; }

    public abstract String getTipo();
    public abstract String toCSV();
}
