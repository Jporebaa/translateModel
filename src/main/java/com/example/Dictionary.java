package com.example;

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
        if (!wordToIndex.containsKey(word)) {
            wordToIndex.put(word, index);
            indexToWord.put(index, word);
        }
    }

    public int getIndex(String word) {
        return wordToIndex.getOrDefault(word, -1); // -1 for UNKNOWN
    }

    public String getWord(int index) {
        return indexToWord.getOrDefault(index, "UNKNOWN");
    }

    public int size() {
        return wordToIndex.size();  // Return the size of the dictionary
    }
}
