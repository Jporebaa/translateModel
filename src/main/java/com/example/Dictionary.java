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
        word = normalizeText(word);
        if (!wordToIndex.containsKey(word)) {
            wordToIndex.put(word, index);
            indexToWord.put(index, word);
            System.out.println("Dodano słowo: " + word + " na indeksie: " + index);
        }
    }

    public int getIndex(String word) {
        word = normalizeText(word);
        return wordToIndex.getOrDefault(word, -1); // Zwraca -1 jeśli słowo nie jest znane
    }

    public String getWord(int index) {
        return indexToWord.getOrDefault(index, "UNKNOWN");
    }

    public int size() {
        return wordToIndex.size();
    }

    private String normalizeText(String text) {
        text = text.toLowerCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", "");
        return text.replaceAll("\\s+", " ").trim();
    }
}
