package com.estacionespacial.model.modulo;

public class ModuloInvestigacion extends Modulo {
    private String campo;

    public ModuloInvestigacion(String codigo, String nombre, int cap, boolean op, String campo) {
        super(codigo, nombre, cap, op);
        if (campo == null || campo.isBlank()) throw new IllegalArgumentException("Campo inválido");
        this.campo = campo;
    }

    public String getCampoInvestigacion() { return campo; }
    public void setCampoInvestigacion(String c) {
        if (c == null || c.isBlank()) throw new IllegalArgumentException("Campo inválido");
        this.campo = c;
    }

    @Override
    public String getTipo() { return "Investigación"; }

    @Override
    public String toCSV() {
        return String.join(";", getCodigo(), getNombre(), getTipo(),
                           String.valueOf(getCapacidad()), String.valueOf(isOperativo()),
                           campo);
    }
}
