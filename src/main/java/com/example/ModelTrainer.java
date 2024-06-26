package com.example;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.util.ArrayList;
import java.util.List;

public class ModelTrainer {
    private static final int batchSize = 10;
    private static final int numEpochs = 25;

    public ListDataSetIterator<DataSet> prepareDataSet(List<String[]> trainingData, TextProcessor textProcessor, int vocabularySize) {
        List<DataSet> dataSets = new ArrayList<>();
        for (String[] pair : trainingData) {
            INDArray inputVector = textProcessor.encode(pair[0], vocabularySize);
            INDArray outputVector = textProcessor.encode(pair[1], vocabularySize);
            DataSet dataSet = new DataSet(inputVector, outputVector);
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
            dataSetIterator.reset(); // Resetowanie iteratora na koniec każdej epoki
        }
    }

    public void retrainModel(MultiLayerNetwork model, TextProcessor textProcessor, List<String[]> newTrainingData, int vocabularySize) {
        ListDataSetIterator<DataSet> dataSetIterator = prepareDataSet(newTrainingData, textProcessor, vocabularySize);
        trainModel(model, dataSetIterator);
    }
}
