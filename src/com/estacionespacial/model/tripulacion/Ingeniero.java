</details> <details> <summary><code>Ingeniero.java</code></summary>
package com.estacionespacial.model.tripulacion;

public class Ingeniero extends Tripulante {
    private String especialidad;

    public Ingeniero(String id, String nombre, String especialidad, double horas) {
        super(id, nombre, "Ingeniero");
        this.especialidad = especialidad;
        trabajar(horas);
    }
    public Ingeniero(String id, String nombre, String especialidad) {
        this(id, nombre, especialidad, 0);
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String esp) {
        if (esp == null || esp.isBlank()) throw new IllegalArgumentException("Especialidad inválida");
        this.especialidad = esp;
    }

    @Override
    public String getInfoCompleta() {
        return String.format("ID:%s, Nombre:%s, Rol:%s, Especialidad:%s, Horas:%.2f",
            getId(), getNombre(), getRol(), especialidad, getHorasTrabajo());
    }

    @Override
    public String toCSV() {
        return super.toCSV() + ";" + especialidad;
    }
}
