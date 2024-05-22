package com.example;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private Map<String, Integer> wordToIndex;
    private Map<Integer, String> indexToWord;

    public Dictionary() {
        wordToIndex = new HashMap<>();
        indexToWord = new HashMap<>();
    }

    public void addWord(String word, int index) {
        word = normalizeText(word); // Normalizacja tekstu podczas dodawania
        if (!wordToIndex.containsKey(word)) {
            wordToIndex.put(word, index);
            indexToWord.put(index, word);
        }
    }

    public int getIndex(String word) {
        word = normalizeText(word); // Normalizacja tekstu podczas wyszukiwania
        return wordToIndex.getOrDefault(word, -1); // -1 for UNKNOWN
    }

    public String getWord(int index) {
        return indexToWord.getOrDefault(index, "UNKNOWN");
    }

    public int size() {
        return wordToIndex.size();  // Return the size of the dictionary
    }

    private String normalizeText(String text) {
        text = text.toLowerCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return text.replaceAll("\\s+", " ").trim(); // Dodatkowe usuwanie białych znaków
    }
}
