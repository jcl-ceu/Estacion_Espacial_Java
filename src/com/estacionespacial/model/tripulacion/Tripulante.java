<details> <summary><code>Tripulante.java</code></summary>
package com.estacionespacial.model.tripulacion;

public abstract class Tripulante {
    private String id, nombre, rol;
    private double horasTrabajo;

    public Tripulante(String id, String nombre, String rol) {
        this.id = id; this.nombre = nombre; this.rol = rol;
        this.horasTrabajo = 0;
    }

    // getters y setters con validaciones
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
    public double getHorasTrabajo() { return horasTrabajo; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre;
    }
    public void setRol(String rol) {
        if (rol == null || rol.isBlank()) throw new IllegalArgumentException("Rol inválido");
        this.rol = rol;
    }

    public abstract String getInfoCompleta();

    public void trabajar(double horas) {
        if (horas <= 0) throw new IllegalArgumentException("Horas deben ser positivas");
        this.horasTrabajo += horas;
    }
    public void descansar() { this.horasTrabajo = 0; }

    public String toCSV() {
        return String.join(";", id, nombre, rol, String.valueOf(horasTrabajo));
    }
    public static String[] fromCSV(String line) {
        return line.split(";");
    }
}
</details>
