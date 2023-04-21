package it.polimi.ingsw;

import it.polimi.ingsw.launcher.argparser.CLIDestinations;
import it.polimi.ingsw.launcher.argparser.CLIParser;
import it.polimi.ingsw.launcher.parameters.*;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        Boolean isVerbose = result.get(CLIDestinations.VERBOSE);
        boolean areArgumentsExhaustive = parser.areArgumentsExhaustive(result);

        if (areArgumentsExhaustive) {
            // we read the values and assemble the configuration
            AppLaunchTarget target = result.get(CLIDestinations.TARGET);
            String serverIp = result.get(CLIDestinations.SERVER_IP);
            int serverPort = result.get(CLIDestinations.SERVER_PORT);
            ClientMode modePreselection = result.get(CLIDestinations.CLIENT_MODE);
            ClientProtocol protocolPreselection = result.get(CLIDestinations.CLIENT_PROTOCOL);

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
     * */
    private static @NotNull ExhaustiveLaunchConfiguration launchWizardForConfig(Namespace nonExhaustivePresetConfig) {
        return new ExhaustiveLaunchConfiguration(
                AppLaunchTarget.SERVER,
                "localhost", 8080,
                List.of()
        );
    }

    /**
     * Starts the
     * */
    private static void launchConfiguration(ExhaustiveLaunchConfiguration config) {
        switch (config.appLaunchTarget()) {
            case SERVER -> {
                startServer(config.serverIp(), config.serverPort());
            }
            case CLIENT -> {
                ClientExhaustiveConfiguration clientConfig = config.clientConfigurations().get(0);
                startClient(config.serverIp(), config.serverPort(), clientConfig.mode(), clientConfig.protocol());
            }
            case SERVER_AND_CLIENT -> {
                startServer(config.serverIp(), config.serverPort());

                ClientExhaustiveConfiguration clientConfig = config.clientConfigurations().get(0);
                startClient(config.serverIp(), config.serverPort(), clientConfig.mode(), clientConfig.protocol());
            }
        }
    }


    private static void startServer(String serverIp, int serverPort) {

    }

    private static void startClient(String serverIp, int serverPort, @Nullable ClientMode mode, @Nullable ClientProtocol protocol) {

    }

}
