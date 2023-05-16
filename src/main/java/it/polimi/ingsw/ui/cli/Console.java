package it.polimi.ingsw.ui.cli;

import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static void out(Object string) {
        System.out.print(string);
    }

    public static String in() {
        return scanner.nextLine();
    }

    public static void flush(){
        System.out.flush();
    }
}
