package com.estacionespacial;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.cargarDatos();
        sistema.mostrarMenu();
        sistema.guardarDatos();
    }
}
