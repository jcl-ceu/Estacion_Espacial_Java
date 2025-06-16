<details> <summary><code>ModuloHabitacional.java</code></summary>
package com.estacionespacial.model.modulo;

public class ModuloHabitacional extends Modulo {
    private int camas;

    public ModuloHabitacional(String codigo, String nombre, int capacidad, boolean op, int camas) {
        super(codigo, nombre, capacidad, op);
        this.camas = camas;
    }

    public int getCamas() { return camas; }
    public void setCamas(int c) {
        if (c < 0) throw new IllegalArgumentException("Camas inválidas");
        this.camas = c;
    }

    @Override
    public String getTipo() { return "Habitacional"; }

    @Override
    public String toCSV() {
        return String.join(";", getCodigo(), getNombre(), getTipo(),
                           String.valueOf(getCapacidad()), String.valueOf(isOperativo()),
                           String.valueOf(camas));
    }
}
</details>
