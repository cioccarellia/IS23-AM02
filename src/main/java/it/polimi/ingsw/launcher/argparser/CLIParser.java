package it.polimi.ingsw.launcher.argparser;

import it.polimi.ingsw.App;
import it.polimi.ingsw.launcher.parameters.AppLaunchTarget;
import it.polimi.ingsw.launcher.parameters.ClientMode;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;

/**
 * Class implementing a basic CLI parser for the startup program
 */
public class CLIParser {

    private final String programName;

    public CLIParser(String _programName) {
        programName = _programName;
    }

    private void injectArguments(@NotNull ArgumentParser parser) {
        parser.addArgument("-v", "--verbose")
                .dest(CLIDestinations.VERBOSE)
                .type(Boolean.class)
                .setConst(true)
                .setDefault(false)
                .help("Enable verbose output.");

        parser.addArgument("-t", "--target")
                .dest(CLIDestinations.TARGET)
                .type(AppLaunchTarget.class)
                .help("Defines the type of configuration launched.");

        parser.addArgument("--server-address")
                .dest(CLIDestinations.SERVER_IP_AND_PORT)
                .metavar("HOST:PORT")
                .help("The server address");

        parser.addArgument("--client-mode")
                .dest(CLIDestinations.CLIENT_MODE)
                .type(ClientMode.class)
                .help("Defines the mode for the current client.");

        parser.addArgument("--client-username")
                .dest(CLIDestinations.CLIENT_USERNAME)
                .type(String.class)
                .help("Sets the player username.");

        parser.addArgument("--client-protocol")
                .dest(CLIDestinations.CLIENT_PROTOCOL)
                .type(ClientProtocol.class)
                .help("Defines the protocol for the current client.");
    }


    /**
     * Returns whether the given arguments are exhaustive and can allow for an immediate start
     */
    public boolean areArgumentsExhaustive(Namespace ns) {
        AppLaunchTarget config = ns.get("config");

        if (config == null || ns.get("ip_and_port") == null) {
            return false;
        }

        switch (config) {
            case SERVER -> {
                return true;
            }
            case CLIENT, SERVER_AND_CLIENT -> {
                String username = ns.get(CLIDestinations.CLIENT_USERNAME);
                ClientMode mode = ns.get(CLIDestinations.CLIENT_MODE);
                ClientProtocol proto = ns.get(CLIDestinations.CLIENT_PROTOCOL);

                return username != null && mode != null && proto != null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + config);
        }
    }

    public Namespace parse(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor(programName).build()
                .description("MyShelfie game");

        injectArguments(parser);

        try {
            return parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            App.logger.error("Error parsing CLI arguments", e);
            throw new RuntimeException(e);
        }
    }
}