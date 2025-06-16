package com.estacionespacial.model.modulo;

public class ModuloMando extends Modulo {
    private boolean tieneIA;

    public ModuloMando(String codigo, String nombre, int cap, boolean op, boolean ia) {
        super(codigo, nombre, cap, op);
        this.tieneIA = ia;
    }

    public boolean isTieneIA() { return tieneIA; }
    public void setTieneIA(boolean ia) { this.tieneIA = ia; }

    @Override
    public String getTipo() { return "Mando"; }

    @Override
    public String toCSV() {
        return String.join(";", getCodigo(), getNombre(), getTipo(),
                           String.valueOf(getCapacidad()), String.valueOf(isOperativo()),
                           String.valueOf(tieneIA));
    }
}
