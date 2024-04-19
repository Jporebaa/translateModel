package com.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

public class Translator {
    private final MultiLayerNetwork model;
    private final TextProcessor textProcessor;  // Załóżmy, że mamy klasę do przetwarzania tekstu

    public Translator(MultiLayerNetwork model, TextProcessor textProcessor) {
        this.model = model;
        this.textProcessor = textProcessor;  // Teraz textProcessor jest prawidłowo inicjalizowany
    }

    public String translate(String input) {
        // Tokenizacja i kodowanie tekstu wejściowego
        int[] encodedInput = textProcessor.encode(input, 100); // 100 to przykładowy rozmiar słownika
        double[] doubleEncodedInput = Arrays.stream(encodedInput).asDoubleStream().toArray();

        // Utworzenie macierzy z wektora cech, gdzie 1 oznacza liczbę wierszy, a encodedInput.length liczbę kolumn
        INDArray inputArray = Nd4j.create(doubleEncodedInput, new long[]{1, doubleEncodedInput.length});

        // Przewidywanie modelu
        INDArray outputArray = model.output(inputArray);

        // Dekodowanie przewidywań do formy tekstowej
        String translatedText = textProcessor.decode(outputArray); // Zakładając, że taka metoda istnieje
        return translatedText;
    }
}
