package com.example;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

public class TextProcessor {
    private Dictionary dictionary;

    public TextProcessor(Dictionary dictionary) {
        this.dictionary = dictionary;
        initializeDictionary();
    }

    private void initializeDictionary() {
        String[] exampleWords = {"cześć hello", "świat  world", "example", "test", "translate", "cześć", "dziękuję", "biblioteka", "pies", "koty", "samochód", "książka", "Warszawa", "pracuje", "zdalnie", "grać", "szachy", "hobby", "dzień", "pomóc", "pogoda", "siostry", "przyjacielem", "kawa", "gorąca", "restauracja", "urodziny"};
        int index = 0;
        for (String word : exampleWords) {
            dictionary.addWord(word, index++);
        }
        dictionary.addWord("UNKNOWN", index);
    }

    public INDArray encode(String text, int vectorSize) {
        double[] vector = new double[vectorSize];
        String[] words = text.split("\\s+");
        boolean found = false;
        for (String word : words) {
            int index = dictionary.getIndex(word);
            if (index < vectorSize) {
                vector[index] = 1.0;
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono indeksu dla: " + text);
        }
        return Nd4j.create(vector, new long[]{1, vectorSize});
    }

    public String decode(INDArray outputArray) {
        int resultIndex = outputArray.argMax(1).getInt(0);
        return dictionary.getWord(resultIndex);
    }
}
