package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void normalizeShouldLowerCaseAndReplaceYo() {
        String result = WordleDictionary.normalize(" ЁЖИК ");
        assertEquals("ежик", result);
    }

    @Test
    void buildHintAllCorrect() {
        String hint = WordleDictionary.buildHint("герой", "герой");
        assertEquals("+++++", hint);
    }

    @Test
    void buildHintAllWrong() {
        String hint = WordleDictionary.buildHint("aaaaa", "ббббб");
        assertEquals("-----", hint);
    }

    @Test
    void buildHintMixed() {
        String hint = WordleDictionary.buildHint("гонец", "герой");
        assertEquals("+^-^-", hint);
    }

    @Test
    void buildHintWithRepeatedLetters() {
        String hint = WordleDictionary.buildHint("самса", "масса");
        assertNotNull(hint);
        assertEquals(5, hint.length());
    }

    @Test
    void containsShouldWork() {
        WordleDictionary dict = new WordleDictionary(List.of("герой", "домик"));
        assertTrue(dict.contains("герой"));
        assertFalse(dict.contains("море"));
    }

    @Test
    void getRandomWordShouldReturnWordFromDictionary() {
        WordleDictionary dict = new WordleDictionary(List.of("герой"));
        assertEquals("герой", dict.getRandomWord());
    }
}

