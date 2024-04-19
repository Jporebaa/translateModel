package com.example;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TextProcessor {
    private Map<String, Integer> wordToIndex;
    private Map<Integer, String> indexToWord;  // Mapa dla dekodowania

    public TextProcessor() {
        this.wordToIndex = new HashMap<>();
        this.indexToWord = new HashMap<>();
        initializeWordToIndex();
    }

    private void initializeWordToIndex() {
        String[] exampleWords = {"hello", "world", "example", "test", "translate"};
        for (int i = 0; i < exampleWords.length; i++) {
            wordToIndex.put(exampleWords[i], i);
            indexToWord.put(i, exampleWords[i]);  // Dodanie odwrotnego mapowania
        }
    }

    public int[] encode(String text, int vectorSize) {
        int[] vector = new int[vectorSize];
        String[] words = text.split("\\s+");
        System.out.println("Encoding text: " + text);  // Debug
        boolean found = false;
        for (String word : words) {
            if (wordToIndex.containsKey(word)) {
                int index = wordToIndex.get(word);
                if (index < vectorSize) {
                    vector[index] = 1;  // Ustawienie na 1 w odpowiedniej pozycji
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No words found in index for: " + text);
        }
        System.out.println("Encoded vector: " + Arrays.toString(vector));  // Debug
        return vector;
    }

    public String decode(INDArray outputArray) {
        System.out.println("Output array: " + outputArray);  // Debug
        int resultIndex = outputArray.argMax(1).getInt(0);
        System.out.println("Chosen index: " + resultIndex);  // Debug
        return indexToWord.getOrDefault(resultIndex, "Unknown");
    }
}
