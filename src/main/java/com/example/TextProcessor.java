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
                "cześć hello", "Cześć Hi", "Do widzenia Goodbye", "Tak Yes", "Nie No",
                "Dziękuje Thank", "Proszę Please", "Przepraszam Sorry", "Ja I",
                "Ty You", "On He", "Ona She", "Ono It", "My We", "Oni They", "Ten This",
                "Tamten That", "Tutaj Here", "Tam There", "Co What", "Kto Who",
                "Gdzie Where", "Kiedy When", "Dlaczego Why", "Jak How", "Jeść Eat",
                "Pić Drink", "Spać Sleep", "Iść Walk", "Biec Run", "Skakać Jump",
                "Siedzieć Sit", "Stać Stand", "Czytać Read", "Pisać Write",
                "Mówić Speak", "Słuchać Listen", "Rozumieć Understand", "Kochać Love",
                "Nienawidzić Hate", "Szczęśliwy Happy", "Smutny Sad", "Zły Angry",
                "Zmęczony Tired", "Gorąc Hot", "Zimno Cold", "duży Big", "Mały Small",
                "Dobry Good", "Zły Bad", "Zwierze Animal", "Jabłko Apple", "Osa Bee",
                "Bank Bank", "Bar Bar", "jak się masz how are you?", "dziękuję bardzo thank you very much",
                "gdzie jest biblioteka? where is the library?", "mam psa I have a dog",
                "ona lubi koty she likes cats", "samochód jest nowy the car is new",
                "to jest moja książka this is my book", "on mieszka w Warszawie he lives in Warsaw",
                "ona pracuje zdalnie she works remotely", "lubię grać w szachy I like to play chess",
                "jakie masz hobby? what are your hobbies?", "to jest piękny dzień it is a beautiful day",
                "czy możesz mi pomóc? can you help me?", "jaka jest pogoda? what is the weather like?",
                "mam dwie siostry I have two sisters", "on jest moim przyjacielem he is my friend",
                "ta kawa jest zbyt gorąca this coffee is too hot",
                "gdzie jest najbliższa restauracja? where is the nearest restaurant?",
                "kiedy jest twój urodziny? when is your birthday?"
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
