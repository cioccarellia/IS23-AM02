package it.polimi.ingsw.ui.game.cli;

import java.util.Scanner;

/**
 * Helper to get the input from CL and to print in CL
 */
public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static void out(Object string) {
        System.out.print(string);
        flush();
    }

    public static String in(String message) {
        out(message + " > ");
        return scanner.nextLine();
    }


    public static String in() {
        return scanner.nextLine();
    }

    private static void flush() {
        System.out.flush();
    }

    public static void outln() {
        System.out.println();
    }

    public static void printnl(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println();
        }
    }
}
