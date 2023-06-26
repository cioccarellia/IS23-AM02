package it.polimi.ingsw.ui.game.cli.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class AsyncStreamReader implements Callable<String> {

    private int DELAY = 200;
    public static boolean isActive = false;

    public String call() {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            isActive = true;
            // wait until we have data to complete a readLine()
            while (!br.ready()) {
                Thread.sleep(DELAY);
            }

            // only read when a "\n" character is present in the input buffer
            input = br.readLine();
        } catch (InterruptedException e) {
            // task interrupted
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ConsoleInputReadTask() general error");
            return null;
        } finally {
            isActive = false;
        }

        return input;
    }
}