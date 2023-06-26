package it.polimi.ingsw.ui.game.cli.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConsoleInputReadTask implements Callable<String> {

    public static boolean isActive = false;

    public String call() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;

        try {
            isActive = true;
            // wait until we have data to complete a readLine()
            while (!br.ready()) {
                Thread.sleep(200);
            }

            // only read when a "\n" character is present in the input buffer
            input = br.readLine();
        } catch (InterruptedException e) {
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