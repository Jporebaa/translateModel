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
            for (String line : lines) {
                String[] split = line.split("\t");
                if (split.length == 2) {
                    dataSet.add(split);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the data file: " + e.getMessage());
            return dataSet; // Returns an empty list if there's an error
        }
        return dataSet;
    }

}

