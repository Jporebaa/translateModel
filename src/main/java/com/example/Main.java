package com.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dataPath = "C:\\Users\\jakub\\IdeaProjects\\translateModel\\src\\main\\java\\com\\example\\translation_data.txt";
        printFilePath(dataPath);

        DataLoader dataLoader = new DataLoader();
        Dictionary dictionary = new Dictionary();
        TextProcessor textProcessor = new TextProcessor(dictionary);
        TranslationModel translationModel = new TranslationModel(100, 100, 50);
        MultiLayerNetwork model = translationModel.getModel();
        Translator translator = new Translator(model, textProcessor);
        ModelTrainer modelTrainer = new ModelTrainer();

        java.util.List<String[]> trainingData = dataLoader.loadDataSet(dataPath);
        org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator<org.nd4j.linalg.dataset.DataSet> dataSetIterator = modelTrainer.prepareDataSet(trainingData, textProcessor, 100);
        modelTrainer.trainModel(model, dataSetIterator);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Wpisz polskie słowo, aby przetłumaczyć je na angielski ('exit' to wyjście, 'add' to dodanie nowej pary słów):");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            } else if ("add".equalsIgnoreCase(input)) {
                System.out.println("Podaj polskie słowo:");
                String polishWord = scanner.nextLine();
                System.out.println("Podaj angielskie tłumaczenie:");
                String englishWord = scanner.nextLine();
                int newIndex = dictionary.size();
                dictionary.addWord(polishWord, newIndex);
                dictionary.addWord(englishWord, newIndex);
                trainingData.add(new String[]{polishWord, englishWord});
                modelTrainer.retrainModel(model, textProcessor, trainingData, 100); // Retrenowanie modelu z nowymi danymi
            } else {
                String translatedText = translator.translate(input);
                System.out.println("Przetłumaczony tekst: " + translatedText);
            }
        }
        scanner.close();
    }

    private static void printFilePath(String filePath) {
        System.out.println("Ścieżka do pliku z danymi: " + filePath);
    }
}
