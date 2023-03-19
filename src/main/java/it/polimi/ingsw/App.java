package it.polimi.ingsw;

import it.polimi.ingsw.launcher.LauncherParameters;
import picocli.CommandLine;

/**
 * Program entry point. Depending on what is requested, either the client,
 * the server, or a configuration of them may be started.
 */
public class App {

    public static void main(String[] args) {
        LauncherParameters parameters = new LauncherParameters();
        CommandLine cli = new CommandLine(parameters);

        cli.parseArgs(args);

        assert (parameters.isClient ^ parameters.isServer);
    }
}
