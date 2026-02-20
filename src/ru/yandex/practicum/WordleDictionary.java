package ru.yandex.practicum;

import java.util.List;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */


public class WordleDictionary {

    private final List<String> words;
    private final Random random = new Random();

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getAllWords() {
        return new ArrayList<>(words);
    }

    public static String normalize(String word) {
        return word.trim()
                .toLowerCase()
                .replace('ё', 'е');
    }

    public static String buildHint(String guess, String answer) {

        char[] result = new char[5];
        boolean[] used = new boolean[5];

        // Первый проход — "+"
        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        // Второй проход — "^" или "-"
        for (int i = 0; i < 5; i++) {

            if (result[i] == '+') continue;

            boolean found = false;

            for (int j = 0; j < 5; j++) {
                if (!used[j] && guess.charAt(i) == answer.charAt(j)) {
                    found = true;
                    used[j] = true;
                    break;
                }
            }

            result[i] = found ? '^' : '-';
        }

        return new String(result);
    }
}
