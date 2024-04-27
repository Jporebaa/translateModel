package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<String[]> loadDataSet(String filePath) {
        List<String[]> dataSet = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Logowanie ilości wczytanych linii
            if (lines.isEmpty()) {
                System.out.println("Plik danych jest pusty.");
            } else {
                System.out.println("Plik danych zawiera " + lines.size() + " linii.");
            }

            for (String line : lines) {
                String[] split = line.split("\t");
                if (split.length == 2) {
                    dataSet.add(split);
                } else {
                    // Logowanie problemów z formatem danych
                    System.out.println("Nieprawidłowy format linii: " + line + " (oczekiwane 2 kolumny, rozdzielone tabulatorem)");
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas czytania pliku danych: " + e.getMessage());
        }
        return dataSet;
    }
}
