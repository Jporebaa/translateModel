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
        String[] exampleWords = {
                "cześć hello", "do widzenia goodbye", "tak yes", "nie no",
                "dziękuję thank", "proszę please", "przepraszam sorry", "ja i",
                "ty you", "on he", "ona she", "ono it", "my we", "oni they",
                "ten this", "tamten that", "tutaj here", "tam there", "co what",
                "kto who", "gdzie where", "kiedy when", "dlaczego why", "jak how",
                "jeść eat", "pić drink", "spać sleep", "iść walk", "biec run",
                "skakać jump", "siedzieć sit", "stać stand", "czytać read",
                "pisać write", "mówić speak", "słuchać listen", "rozumieć understand",
                "kochać love", "nienawidzić hate", "szczęśliwy happy", "smutny sad",
                "zły angry", "zmęczony tired", "gorąc hot", "zimno cold", "duży big",
                "mały small", "dobry good", "zły bad", "zwierzę animal", "jabłko apple",
                "osa bee", "bank bank", "bar bar"
        };

        int index = 0;
        for (String entry : exampleWords) {
            String[] words = entry.split("\\s+");
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
        Arrays.fill(vector, 0.0);
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
        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", ""); // Usuwanie znaków specjalnych
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
