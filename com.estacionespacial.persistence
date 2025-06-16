<details> <summary><code>CSVHandler.java</code></summary>
package com.estacionespacial.persistence;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVHandler {
    private static final String SEP = ";";

    public static List<String[]> leer(String ruta) throws IOException {
        List<String[]> filas = new ArrayList<>();
        if (!Files.exists(Paths.get(ruta))) return filas;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                filas.add(linea.split(SEP));
            }
        }
        return filas;
    }

    public static void escribir(String ruta, List<String[]> filas) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(ruta))) {
            for (String[] f : filas) {
                bw.write(String.join(SEP, f));
                bw.newLine();
            }
        }
    }
}
</details>
