package it.polimi.ingsw.ui.game.cli.printer;

import it.polimi.ingsw.ui.game.cli.Console;


public class cliFormatiPrinter {
    private static int distanceBetweenUsernameAndTokenPoints = 23;
    private static int distanceBetweenTokenPointsAndPersonalGoalCardPoints = 36;
    private static int getDistanceBetweenPersonalGoalCardPointsAndBookshelfPoints = 17;
    private static int ifOneCifer = 1;
    private static int ifTwoCifers = 2;
    private static int ifThreeCifers = 3;

    public static void printSpaceAfterUsername(int lenght) {
        int realLenght = 17 - lenght;
        for (int i = 0; i < realLenght; i++) {
            Console.out(" ");
        }
    }

    public static void printSpaceAfterTokenPoints(int lenght) {
        int realLenght;

        if (lenght < 10) {
            realLenght = distanceBetweenUsernameAndTokenPoints - ifOneCifer;
        } else if (lenght > 10) {
            realLenght = distanceBetweenUsernameAndTokenPoints - ifTwoCifers;
        } else realLenght = distanceBetweenUsernameAndTokenPoints - ifThreeCifers;

        for (int i = 0; i < realLenght; i++) {
            Console.out(" ");
        }
    }
    public static void printSpaceAfterPersonalGoalCardPoints(int lenght) {
        int realLenght;

        if (lenght < 10) {
            realLenght = distanceBetweenTokenPointsAndPersonalGoalCardPoints - ifOneCifer;
        } else if (lenght > 10) {
            realLenght = distanceBetweenTokenPointsAndPersonalGoalCardPoints - ifTwoCifers;
        } else realLenght = distanceBetweenTokenPointsAndPersonalGoalCardPoints - ifThreeCifers;

        for (int i = 0; i < realLenght; i++) {
            Console.out(" ");
        }
    }
    public static void printSpaceAfterBookshelfPoints(int lenght) {
        int realLenght;

        if (lenght < 10) {
            realLenght = getDistanceBetweenPersonalGoalCardPointsAndBookshelfPoints - ifOneCifer;
        } else if (lenght > 10) {
            realLenght = getDistanceBetweenPersonalGoalCardPointsAndBookshelfPoints - ifTwoCifers;
        } else realLenght = getDistanceBetweenPersonalGoalCardPointsAndBookshelfPoints - ifThreeCifers;

        for (int i = 0; i < realLenght; i++) {
            Console.out(" ");
        }
    }
}
