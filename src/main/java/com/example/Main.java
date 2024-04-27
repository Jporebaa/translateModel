package com.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Definiowanie ścieżki do pliku z danymi testowymi
        String dataPath = "C:\\Users\\jakub\\IdeaProjects\\translateModel\\src\\main\\java\\com\\example\\translation_data.txt";

        // Wywołanie metody do wypisywania ścieżki
        printFilePath(dataPath);

        DataLoader dataLoader = new DataLoader();
        Dictionary dictionary = new Dictionary();
        TextProcessor textProcessor = new TextProcessor(dictionary);
        TranslationModel translationModel = new TranslationModel(100, 100, 50);
        MultiLayerNetwork model = translationModel.getModel();
        Translator translator = new Translator(model, textProcessor);

        java.util.List<String[]> trainingData = dataLoader.loadDataSet(dataPath);

        ModelTrainer modelTrainer = new ModelTrainer();
        org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator<org.nd4j.linalg.dataset.DataSet> dataSetIterator = modelTrainer.prepareDataSet(trainingData, textProcessor, 100);
        modelTrainer.trainModel(model, dataSetIterator);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Witaj w translatorze polsko-angielskim!");
            while (true) {
                System.out.println("Wpisz polskie słowo, aby przetłumaczyć je na angielski ('exit' to wyjście):");
                String input = scanner.nextLine();

                if ("exit".equalsIgnoreCase(input)) {
                    break;
                }

                String translatedText = translator.translate(input);
                System.out.println("Przetłumaczony tekst: " + translatedText);
            }
        }
    }

    // Metoda do wypisywania ścieżki pliku
    private static void printFilePath(String filePath) {
        System.out.println("Ścieżka do pliku z danymi: " + filePath);
    }
}
