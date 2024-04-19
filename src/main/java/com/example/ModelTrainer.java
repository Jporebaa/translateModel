package com.example;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

public class ModelTrainer {
    private static final int batchSize = 10;
    private static final int numEpochs = 10; // Definiowanie liczby epok treningowych

    public ListDataSetIterator<DataSet> prepareDataSet(List<String[]> trainingData, TextProcessor textProcessor, int vocabularySize) {
        List<DataSet> dataSets = new ArrayList<>();
        for (String[] pair : trainingData) {
            int[] inputVector = textProcessor.encode(pair[0], vocabularySize);
            int[] outputVector = textProcessor.encode(pair[1], vocabularySize);
            DataSet dataSet = new DataSet(Nd4j.createFromArray(inputVector), Nd4j.createFromArray(outputVector));
            dataSets.add(dataSet);
        }
        return new ListDataSetIterator<>(dataSets, batchSize);
    }

    public void trainModel(MultiLayerNetwork model, ListDataSetIterator<DataSet> dataSetIterator) {
        for (int epoch = 0; epoch < numEpochs; epoch++) {
            while (dataSetIterator.hasNext()) {
                DataSet dataSet = dataSetIterator.next();
                model.fit(dataSet);
            }
            System.out.println("Completed epoch: " + epoch + ", Current loss: " + model.score());
            dataSetIterator.reset(); // Resetowanie iteratora na koniec każdej epoki
        }
    }
}
