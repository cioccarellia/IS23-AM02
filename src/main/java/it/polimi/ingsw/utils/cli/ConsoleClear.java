package it.polimi.ingsw.utils.cli;

public class ConsoleClear {
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            String[] commands;

            if (os.contains("Windows")) {
                commands = new String[]{"cls"};
            } else {
                commands = new String[]{"clear"};
            }

            Runtime.getRuntime().exec(commands);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
