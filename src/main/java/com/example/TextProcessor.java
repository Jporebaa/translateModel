package com.example;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import java.text.Normalizer;
import java.util.Arrays;

public class TextProcessor {
    private Dictionary dictionary;

    public TextProcessor(Dictionary dictionary) {
        this.dictionary = dictionary;
        initializeDictionary();
    }

    private void initializeDictionary() {
        String[] exampleWords = {"cześć  hello", "ja I", "jestem  am", "Michał  Michael"};
        int index = 0;
        for (String entry : exampleWords) {
            String[] words = entry.split("\\s+");
            addWord(normalizeText(words[0]), index);
            addWord(normalizeText(words[1]), index);
            index++;
        }
    }

    public void addWord(String word, int index) {
        dictionary.addWord(word, index);
    }

    public INDArray encode(String text, int vectorSize) {
        text = normalizeText(text);
        double[] vector = new double[vectorSize];
        Arrays.fill(vector, 0.0);
        String[] words = text.split("\\s+");
        for (String word : words) {
            int index = dictionary.getIndex(word);
            if (index != -1 && index < vectorSize) {
                vector[index] = 1.0;
                System.out.println("Znaleziono i zakodowano słowo: " + word + " na indeksie: " + index);
            } else {
                System.out.println("Nie znaleziono słowa: " + word);
            }
        }
        return Nd4j.create(vector, new long[]{1, vectorSize});
    }

    public String decode(INDArray outputArray) {
        int resultIndex = outputArray.argMax(1).getInt(0);
        if (resultIndex >= dictionary.size()) {
            System.out.println("Wybrany indeks: " + resultIndex + " jest poza zakresem");
            return "UNKNOWN";
        }
        String translatedWord = dictionary.getWord(resultIndex);
        System.out.println("Wybrany indeks: " + resultIndex);
        System.out.println("Przetłumaczone słowo: " + translatedWord);
        return translatedWord;
    }


    private String normalizeText(String text) {
        text = text.toLowerCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", "");
        return text.replaceAll("\\s+", " ").trim();
    }
}
