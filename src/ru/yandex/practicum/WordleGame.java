package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleGame {

    public static final int MAX_ATTEMPTS = 6;

    private final String answer;
    private final WordleDictionary dictionary;
    private final PrintWriter log;

    private int attemptsLeft = MAX_ATTEMPTS;
    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.log = log;
        log.println("Загаданное слово: " + answer);
    }

    public String makeMove(String guess)
            throws InvalidWordFormatException,
            WordNotFoundInDictionaryException {

        guess = WordleDictionary.normalize(guess);

        validateFormat(guess);

        if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionaryException("Слова нет в словаре.");
        }

        attemptsLeft--;

        String hint = WordleDictionary.buildHint(guess, answer);

        guesses.add(guess);
        hints.add(hint);

        return hint;
    }

    private void validateFormat(String word)
            throws InvalidWordFormatException {

        if (word.length() != WordleDictionary.WORD_LENGTH) {
            throw new InvalidWordFormatException(
                    "Слово должно состоять из "
                            + WordleDictionary.WORD_LENGTH
                            + " букв.");
        }

        if (!word.matches("[а-я]{" + WordleDictionary.WORD_LENGTH + "}")) {
            throw new InvalidWordFormatException("Только русские буквы.");
        }
    }

    public boolean isWon() {
        return !hints.isEmpty()
                && hints.get(hints.size() - 1)
                .equals("+".repeat(WordleDictionary.WORD_LENGTH));
    }

    public boolean isGameOver() {
        return isWon() || attemptsLeft == 0;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getAnswer() {
        return answer;
    }

    public String suggestWord() {

        List<String> candidates = dictionary.getAllWords();

        for (int i = 0; i < guesses.size(); i++) {

            String guess = guesses.get(i);
            String hint = hints.get(i);

            List<String> filtered = new ArrayList<>();

            for (String candidate : candidates) {
                if (WordleDictionary
                        .buildHint(guess, candidate)
                        .equals(hint)) {

                    filtered.add(candidate);
                }
            }

            candidates = filtered;
        }

        if (candidates.isEmpty()) {
            throw new RuntimeException("Нет возможных вариантов.");
        }

        return candidates.get(
                new Random().nextInt(candidates.size()));
    }
}