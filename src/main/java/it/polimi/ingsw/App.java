package it.polimi.ingsw;

import it.polimi.ingsw.launcher.AppLaunchConfiguration;
import it.polimi.ingsw.launcher.ClientMode;
import it.polimi.ingsw.launcher.ClientProtocol;
import it.polimi.ingsw.launcher.argparser.CLIDestinations;
import it.polimi.ingsw.launcher.argparser.CLIParser;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Program entry point. Depending on what is requested, either the client,
 * the server, or a configuration of them may be started.
 */
public class App {

    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting app with args={}", (Object) args);

        CLIParser parser = new CLIParser("myshelfie");

        Namespace result = parser.parse(args);
        logger.info("Arguments successfully parsed");

        boolean areArgumentsExhaustive = parser.areArgumentsExhaustive(result);

        if (areArgumentsExhaustive) {
            Boolean isVerbose = result.get(CLIDestinations.VERBOSE);
            AppLaunchConfiguration config = result.get(CLIDestinations.CONFIG);
            String ipAndPort = result.get(CLIDestinations.IP_AND_PORT);
            ClientMode optionalModePreselection = result.get(CLIDestinations.CLIENT_MODE);
            ClientProtocol optionalProtocolPreselection = result.get(CLIDestinations.CLIENT_PROTOCOL);


            switch (config) {
                case SERVER -> startServer(ipAndPort);
                case CLIENT -> {
                    startClient(ipAndPort, optionalModePreselection, optionalProtocolPreselection);
                }
                case CLIENT_AND_SERVER -> {
                    startServer(ipAndPort);
                    startClient(ipAndPort, optionalModePreselection, optionalProtocolPreselection);
                }
                default -> throw new IllegalStateException("Unexpected value: " + config);
            }
        } else {
            // start wizard
        }
    }


    private static void startServer(String serverIpAndPort) {

    }

    private static void startClient(String serverIpAndPort, @Nullable ClientMode mode, @Nullable ClientProtocol protocol) {

    }


}
