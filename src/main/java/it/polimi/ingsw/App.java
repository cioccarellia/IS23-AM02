package it.polimi.ingsw;

import it.polimi.ingsw.launcher.argparser.CLIParser;
import it.polimi.ingsw.launcher.parameters.*;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.launcher.argparser.CLIDestinations.*;
import static it.polimi.ingsw.launcher.parameters.AppLaunchTarget.SERVER;

/**
 * Program entry point. Depending on what is requested, either the client,
 * the server, or a configuration of them may be started.
 */
public class App {

    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting app with args={}", Arrays.asList(args));

        CLIParser parser = new CLIParser("myshelfie");

        Namespace result = parser.parse(args);
        logger.info("Arguments successfully parsed");

        Boolean isVerbose = result.get(VERBOSE);
        boolean areArgumentsExhaustive = parser.areArgumentsExhaustive(result);

        if (areArgumentsExhaustive) {
            // we read the values and assemble the configuration
            AppLaunchTarget target = result.get(TARGET);
            String serverIp = result.get(SERVER_IP);
            int serverPort = result.get(SERVER_PORT);
            ClientUiMode modePreselection = result.get(CLIENT_MODE);
            ClientProtocol protocolPreselection = result.get(CLIENT_PROTOCOL);

            var clientConfig = new ClientExhaustiveConfiguration(modePreselection, protocolPreselection);
            var finalConfig = new ExhaustiveLaunchConfiguration(target, serverIp, serverPort, List.of(clientConfig));

            launchConfiguration(finalConfig);
        } else {
            // start wizard
            ExhaustiveLaunchConfiguration finalConfig = launchWizardForConfig(result);
            launchConfiguration(finalConfig);
        }
    }

    /**
     * Launches a CLI interface for retrieving the missing parameters
     */
    private static @NotNull ExhaustiveLaunchConfiguration launchWizardForConfig(Namespace nonExhaustivePresetConfig) {
        return new ExhaustiveLaunchConfiguration(
                SERVER,
                "localhost", 8080,
                List.of()
        );
    }

    /**
     * Starts the
     */
    private static void launchConfiguration(ExhaustiveLaunchConfiguration config) {
        switch (config.appLaunchTarget()) {
            case SERVER -> {
                startServer(config.serverIHost(), config.serverPort());
            }
            case CLIENT -> {
                ClientExhaustiveConfiguration clientConfig = config.clientConfigurations().get(0);
                startClient(config.serverIHost(), config.serverPort(), clientConfig.mode(), clientConfig.protocol());
            }
            case SERVER_AND_CLIENT -> {
                startServer(config.serverIHost(), config.serverPort());

                ClientExhaustiveConfiguration clientConfig = config.clientConfigurations().get(0);
                startClient(config.serverIHost(), config.serverPort(), clientConfig.mode(), clientConfig.protocol());
            }
        }
    }


    private static void startServer(String serverIp, int serverPort) {

    }

    private static void startClient(String serverIp, int serverPort, @Nullable ClientUiMode mode, @Nullable ClientProtocol protocol) {

    }

}
