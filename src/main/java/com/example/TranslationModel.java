package com.example;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class TranslationModel {
    private MultiLayerNetwork model;

    public TranslationModel(int inputSize, int outputSize, int hiddenNodes) {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .list()
                .layer(new DenseLayer.Builder().nIn(inputSize).nOut(hiddenNodes)
                        .activation(Activation.RELU).build())
                .layer(new RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT)
                        .activation(Activation.SOFTMAX).nIn(hiddenNodes).nOut(outputSize).build())
                .build();

        model = new MultiLayerNetwork(conf);
        model.init();
    }


    public MultiLayerNetwork getModel() {
        return model;
    }
}
