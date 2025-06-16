<details> <summary><code>RecursoVital.java</code></summary>
package com.estacionespacial.model.recurso;

import com.estacionespacial.exceptions.RecursoInsuficienteException;

public class RecursoVital {
    private String tipo;
    private double cantidadDisponible, consumoDiario;

    public RecursoVital(String tipo, double cantidad, double consumo) {
        this.tipo = tipo;
        this.cantidadDisponible = cantidad;
        this.consumoDiario = consumo;
    }

    public String getTipo() { return tipo; }
    public double getCantidadDisponible() { return cantidadDisponible; }
    public double getConsumoDiario() { return consumoDiario; }

    public void setConsumoDiario(double c) {
        if (c < 0) throw new IllegalArgumentException("Consumo inválido");
        consumoDiario = c;
    }

    public void consumir(double cantidad) throws RecursoInsuficienteException {
        if (cantidad > cantidadDisponible)
            throw new RecursoInsuficienteException("No hay suficiente " + tipo);
        cantidadDisponible -= cantidad;
    }
    public void reabastecer(double cantidad) {
        cantidadDisponible += cantidad;
    }
    public boolean alertaBajoStock() {
        return cantidadDisponible < consumoDiario * 0.2;
    }
    public String getInfo() {
        return String.format("%s: %.2f (consumo diario: %.2f)%s",
            tipo, cantidadDisponible, consumoDiario,
            alertaBajoStock() ? " [ALERTA]" : "");
    }

    public String toCSV() {
        return String.join(";", tipo,
            String.valueOf(cantidadDisponible),
            String.valueOf(consumoDiario));
    }
    public static String[] fromCSV(String line) {
        return line.split(";");
    }
}
</details>
