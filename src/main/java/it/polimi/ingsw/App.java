package it.polimi.ingsw;

import it.polimi.ingsw.launcher.AppLaunchConfiguration;
import it.polimi.ingsw.launcher.ClientMode;
import it.polimi.ingsw.launcher.ClientProtocol;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Program entry point. Depending on what is requested, either the client,
 * the server, or a configuration of them may be started.
 */
public class App {

    protected static final Logger logger = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) {
        logger.info("Starting app with args={}", (Object) args);

        ArgumentParser parser = ArgumentParsers.newFor("myshelfie").build()
                .description("MyShelfie game.");

        parser.addArgument("-v", "--verbose")
                .dest("verbose")
                .type(Boolean.class)
                .setConst(true)
                .help("Enable verbose output.");

        parser.addArgument("-c", "--configuration")
                .dest("config")
                .required(true)
                .type(AppLaunchConfiguration.class)
                .help("Defines the type of configuration launched.");

        parser.addArgument("--server-address")
                .dest("ip_and_port")
                .metavar("HOST:PORT")
                .required(true)
                .help("The server address");

        parser.addArgument("--client-mode")
                .dest("client_mode")
                .type(ClientMode.class)
                .help("Defines the mode for the current client.");

        parser.addArgument("--client-protocol")
                .dest("client_proto")
                .type(ClientProtocol.class)
                .help("Defines the protocol for the current client.");


        try {
            var r = parser.parseArgs(args);

            Boolean isVerbose = r.get("verbose");
            AppLaunchConfiguration config = r.get("config");

            String ipAndPort = r.get("ip_and_port");


            ClientMode optionalModePreselection = r.get("client_mode");
            ClientProtocol optionalProtocolPreselection = r.get("client_proto");


            logger.info("Arguments successfully parsed");

            switch (config) {
                case SERVER -> {
                    startServer(ipAndPort);
                }
                case CLIENT -> {
                    startClient(ipAndPort, optionalModePreselection, optionalProtocolPreselection);
                }
                case CLIENT_AND_SERVER -> {
                    startServer(ipAndPort);
                    startClient(ipAndPort, optionalModePreselection, optionalProtocolPreselection);
                }
                default -> throw new IllegalStateException("Unexpected value: " + config);
            }


        } catch (ArgumentParserException e) {
            logger.error("Exception while parsing arguments", e);
            throw new RuntimeException(e);
        }
    }


    private static void startServer(String serverIpAndPort) {

    }

    private static void startClient(String serverIpAndPort, @Nullable ClientMode mode, @Nullable ClientProtocol protocol) {

    }


}
