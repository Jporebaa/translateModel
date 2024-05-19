package com.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

public class Translator {
    private final MultiLayerNetwork model;
    private final TextProcessor textProcessor;

    public Translator(MultiLayerNetwork model, TextProcessor textProcessor) {
        this.model = model;
        this.textProcessor = textProcessor;
    }

    public String translate(String input) {
        INDArray encodedInput = textProcessor.encode(input, 100);
        INDArray outputArray = model.output(encodedInput);
        return textProcessor.decode(outputArray);
    }
}
