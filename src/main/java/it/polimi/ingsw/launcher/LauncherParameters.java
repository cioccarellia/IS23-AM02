package it.polimi.ingsw.launcher;

import picocli.CommandLine;

public class LauncherParameters {

    @CommandLine.Option(names = {"-c", "--client"}, description = "launches the client")
    public boolean isClient;


    @CommandLine.Option(names = {"-s", "--server"}, description = "launches the server")
    public boolean isServer;

}