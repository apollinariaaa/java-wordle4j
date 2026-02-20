package ru.yandex.practicum;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordleDictionaryLoader {

    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String fileName)
            throws DictionaryLoadException {

        List<String> words = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(fileName),
                                     StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = WordleDictionary.normalize(line);

                if (line.length() == 5 && line.matches("[а-я]{5}")) {
                    words.add(line);
                }
            }

        } catch (IOException e) {
            throw new DictionaryLoadException("Ошибка загрузки словаря.", e);
        }

        if (words.isEmpty()) {
            throw new DictionaryLoadException("Словарь пуст.");
        }

        log.println("Словарь загружен. Размер: " + words.size());

        return new WordleDictionary(words);
    }
}

