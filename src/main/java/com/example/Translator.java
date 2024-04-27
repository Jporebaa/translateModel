package com.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Translator {
    private final MultiLayerNetwork model;
    private final TextProcessor textProcessor;  // Klasa do przetwarzania tekstu

    public Translator(MultiLayerNetwork model, TextProcessor textProcessor) {
        this.model = model;
        this.textProcessor = textProcessor;
    }

    public String translate(String input) {
        // Tokenizacja i kodowanie tekstu wejściowego bezpośrednio do INDArray
        INDArray encodedInput = textProcessor.encode(input, 100); // 100 to przykładowy rozmiar słownika

        // Przewidywanie modelu na podstawie zakodowanego wejścia
        INDArray outputArray = model.output(encodedInput);

        // Dekodowanie przewidywań do formy tekstowej
        String translatedText = textProcessor.decode(outputArray);
        return translatedText;
    }
}
