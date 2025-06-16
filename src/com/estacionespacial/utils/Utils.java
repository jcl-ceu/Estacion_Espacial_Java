<details> <summary><code>Utils.java</code></summary>
package com.estacionespacial.utils;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class Utils {
    private static Scanner sc = new Scanner(System.in);

    public static String leerCadena(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
    public static int leerEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Número inválido");
            }
        }
    }
    public static double leerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Número inválido");
            }
        }
    }
    public static LocalDate leerFecha(String msg) {
        while (true) {
            try {
                System.out.print(msg + " (YYYY-MM-DD): ");
                return LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Fecha inválida");
            }
        }
    }
    public static String generarUUID() {
        return UUID.randomUUID().toString();
    }
    public static void pausa() {
        System.out.println("Presione Enter para continuar...");
        sc.nextLine();
    }
}
</details>
