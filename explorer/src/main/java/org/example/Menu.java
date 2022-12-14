package org.example;

import java.util.Scanner;

public class Menu {
    private final static Scanner in = new Scanner("in");
    public static void startExplorer() {
        while (true) {
            System.out.println("Введите '0', чтобы завершить программу");
            System.out.println("Введите '1', чтобы запустить Explorer");
            String string = in.nextLine();
            if (string == "0") {
                break;
            } else if (string == "1") {
                runExplorer();
            } else {
                System.out.println("НЕПРАВИЛЬНАЯ КОММАНДА");
            }
        }
    }
    private static void runExplorer() {
        while (true) {
            try {
                System.out.print("Введите корневой путь: ");
                Explorer explorer = new Explorer();
                explorer.initialize(in.nextLine());
                explorer.printConcatenationResult();
            } catch (Exception exception) {
                // TODO обработка разных исключений из Explorer
                continue;
            }
            break;
        }
    }
}
