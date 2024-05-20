package com.example;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import java.text.Normalizer;

public class TextProcessor {
    private Dictionary dictionary;

    public TextProcessor(Dictionary dictionary) {
        this.dictionary = dictionary;
        initializeDictionary();
    }

    private void initializeDictionary() {
        String[] exampleWords = {
                "cześć hello", "świat world", "example test", "translate tłumaczyć", "dziękuję thank you",
                "biblioteka library", "pies dog", "koty cats", "samochód car", "książka book",
                "Warszawa Warsaw", "pracuje work", "zdalnie remotely", "grać play", "szachy chess",
                "hobby hobby", "dzień day", "pomóc help", "pogoda weather", "siostry sisters",
                "przyjacielem friend", "kawa coffee", "gorąca hot", "restauracja restaurant", "urodziny birthday",
                "mama mother" // Dodaj więcej par słów
        };

        int index = 0;
        for (String entry : exampleWords) {
            String[] words = entry.split(" ");
            addWord(normalizeText(words[0]), index);  // Dodawanie wersji polskiej
            addWord(normalizeText(words[1]), index);  // Dodawanie wersji angielskiej
            index++;
        }
    }

    public void addWord(String word, int index) {
        dictionary.addWord(word, index);
    }

    public INDArray encode(String text, int vectorSize) {
        text = normalizeText(text);
        double[] vector = new double[vectorSize];
        String[] words = text.split("\\s+");
        boolean found = false;
        for (String word : words) {
            int index = dictionary.getIndex(word);
            if (index != -1 && index < vectorSize) {
                vector[index] = 1.0;
                found = true;
                System.out.println("Znaleziono i zakodowano słowo: " + word + " na indeksie: " + index);
            } else {
                System.out.println("Nie znaleziono słowa: " + word);
            }
        }
        if (!found) {
            System.out.println("Żadne słowo nie zostało znalezione dla: " + text);
        }
        return Nd4j.create(vector, new long[]{1, vectorSize});
    }

    private String normalizeText(String text) {
        text = text.toLowerCase();
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return text.replaceAll("\\s+", " ").trim(); // Dodatkowe usuwanie białych znaków
    }

    public String decode(INDArray outputArray) {
        int resultIndex = outputArray.argMax(1).getInt(0);
        System.out.println("Wybrany indeks: " + resultIndex);
        String translatedWord = dictionary.getWord(resultIndex);
        System.out.println("Przetłumaczone słowo: " + translatedWord);
        return translatedWord;
    }
}
