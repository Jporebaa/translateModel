package com.example;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class TranslationModel {
    private MultiLayerNetwork model;

    public TranslationModel(int inputSize, int outputSize, int hiddenNodes) {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .list()
                .layer(0, new DenseLayer.Builder().nIn(inputSize).nOut(hiddenNodes)
                        .activation(Activation.RELU).build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MCXENT)
                        .activation(Activation.SOFTMAX).nIn(hiddenNodes).nOut(outputSize).build())
                .build();

        model = new MultiLayerNetwork(conf);
        model.init();
    }

    public MultiLayerNetwork getModel() {
        return model;
    }
}
