package it.polimi.ingsw.ui.cli;

import java.util.Scanner;

public class Console {

    public static void out(Object string) {
        System.out.print(string);
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static String in() {
        return scanner.nextLine();
    }
}
