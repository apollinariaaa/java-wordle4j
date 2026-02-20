package ru.yandex.practicum;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */


public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("wordle.log"), true);
             Scanner scanner = new Scanner(System.in)) {

            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dictionary = loader.load("words_ru.txt");

            WordleGame game = new WordleGame(dictionary, log);

            System.out.println("Игра Wordle началась! Угадайте слово из 5 букв.");
            System.out.println("У вас 6 попыток.");

            while (!game.isGameOver()) {

                System.out.println("Осталось попыток: " + game.getAttemptsLeft());
                System.out.print("> ");

                String input = scanner.nextLine();

                try {
                    if (input.isBlank()) {
                        String suggestion = game.suggestWord();
                        System.out.println("Подсказка: " + suggestion);
                        continue;
                    }

                    String hint = game.makeMove(input);
                    System.out.println(hint);

                } catch (InvalidWordFormatException |
                         WordNotFoundInDictionaryException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (game.isWon()) {
                System.out.println("Поздравляем! Вы угадали слово!");
            } else {
                System.out.println("Вы проиграли.");
            }

            System.out.println("Загаданное слово: " + game.getAnswer());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

