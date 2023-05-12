package it.polimi.ingsw.launcher.argparser;

import it.polimi.ingsw.App;
import it.polimi.ingsw.launcher.parameters.AppLaunchTarget;
import it.polimi.ingsw.launcher.parameters.ClientProtocol;
import it.polimi.ingsw.launcher.parameters.ClientUiMode;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static it.polimi.ingsw.launcher.argparser.CLIDestinations.*;

/**
 * Class implementing a basic CLI parser for the startup program
 */
public class CLIParser {

    public static final Logger logger = LoggerFactory.getLogger(CLIParser.class);

    private final String programName;

    public CLIParser(String _programName) {
        programName = _programName;
    }

    private void injectArguments(@NotNull ArgumentParser parser) {
        parser.addArgument("-v", "--verbose")
                .dest(VERBOSE)
                .type(Boolean.class)
                .setConst(true)
                .setDefault(false)
                .help("Enable verbose output.");

        parser.addArgument("-t", "--target")
                .dest(TARGET)
                .type(AppLaunchTarget.class)
                .help("Defines the type of configuration launched.");

        parser.addArgument("--server-address")
                .dest(SERVER_IP)
                .metavar("HOST")
                .help("The server ip address");

        parser.addArgument("--server-tcp-port")
                .dest(SERVER_TCP_PORT)
                .metavar("TCP PORT")
                .type(Integer.class)
                .help("The server port for TCP server");

        parser.addArgument("--server-rmi-port")
                .dest(SERVER_RMI_PORT)
                .metavar("RMI PORT")
                .type(Integer.class)
                .help("The server port for RMI server");

        parser.addArgument("--client-mode")
                .dest(CLIENT_MODE)
                .type(ClientUiMode.class)
                .help("Defines the mode for the current client.");

        parser.addArgument("--client-protocol")
                .dest(CLIENT_PROTOCOL)
                .type(ClientProtocol.class)
                .help("Defines the protocol for the current client.");
    }


    /**
     * Returns whether the given arguments are exhaustive and can allow for an immediate start
     */
    public boolean areArgumentsExhaustive(@NotNull Namespace ns) {
        AppLaunchTarget config = ns.get(TARGET);
        if (config == null) {
            return false;
        }

        int serverTcpPort, serverRmiPort;

        try {
            serverTcpPort = ns.get(SERVER_TCP_PORT);
            serverRmiPort = ns.get(SERVER_RMI_PORT);
        } catch (NullPointerException npe) {
            // no ports given, can't start anything
            return false;
        }

        if (serverRmiPort <= 0 || serverTcpPort <= 0) {
            // verify ports are valid
            return false;
        } else if (serverRmiPort < 1024 || serverTcpPort < 1024) {
            // verify ports are not too low

            return false;
        } else if (serverRmiPort > 65535 || serverTcpPort > 65535) {
            // verify ports are not too high

            return false;
        }

        switch (config) {
            case SERVER -> {
                // we only need ports to boot the server
                return true;
            }
            case CLIENT, SERVER_AND_CLIENT -> {
                // we also need client-specific configuration
                ClientUiMode mode = ns.get(CLIENT_MODE);
                ClientProtocol proto = ns.get(CLIENT_PROTOCOL);
                String serverHost = ns.get(SERVER_IP);

                return serverHost != null && !serverHost.isBlank() && mode != null && proto != null;
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