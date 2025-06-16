<details> <summary><code>Medico.java</code></summary>
package com.estacionespacial.model.tripulacion;

public class Medico extends Tripulante {
    private int pacientesAtendidos;

    public Medico(String id, String nombre, int pacientesAtendidos, double horas) {
        super(id, nombre, "Médico");
        this.pacientesAtendidos = pacientesAtendidos;
        trabajar(horas);
    }
    public Medico(String id, String nombre, int pacientesAtendidos) {
        this(id, nombre, pacientesAtendidos, 0);
    }

    public int getPacientesAtendidos() { return pacientesAtendidos; }
    public void setPacientesAtendidos(int n) {
        if (n < 0) throw new IllegalArgumentException("Pacientes atendidos inválido");
        this.pacientesAtendidos = n;
    }

    @Override
    public String getInfoCompleta() {
        return String.format("ID:%s, Nombre:%s, Rol:%s, Pacientes:%d, Horas:%.2f",
            getId(), getNombre(), getRol(), pacientesAtendidos, getHorasTrabajo());
    }

    @Override
    public String toCSV() {
        return super.toCSV() + ";" + pacientesAtendidos;
    }
}
</details>
