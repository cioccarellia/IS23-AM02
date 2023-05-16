package it.polimi.ingsw;

import it.polimi.ingsw.app.client.AppClient;
import it.polimi.ingsw.app.server.AppServer;
import it.polimi.ingsw.launcher.argparser.CLIParser;
import it.polimi.ingsw.launcher.parameters.*;
import it.polimi.ingsw.ui.wizard.Wizard;
import net.sourceforge.argparse4j.inf.Namespace;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.launcher.argparser.CLIDestinations.*;

/**
 * Program entry point. Depending on what is requested, either the client,
 * the server, or a configuration of them may be started.
 */
public class App {

    public static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final List<AppClient> clients = new ArrayList<>();
    private static AppServer server = null;

    private static void startServer(@NotNull ExhaustiveLaunchConfiguration config) {
        server = new AppServer(config.serverHost(), config.serverTcpPort(), config.serverRmiPort());
    }

    private static void startClient(@NotNull ExhaustiveLaunchConfiguration config) {
        // client proto+ui
        ClientExhaustiveConfiguration clientConfig = config.clientConfigurations().get(0);

        // port matching the selected protocol
        int correctServerProtocolPort = switch (clientConfig.protocol()) {
            case RMI -> config.serverRmiPort();
            case TCP -> config.serverTcpPort();
        };

        AppClient client = new AppClient(clientConfig, config.serverHost(), correctServerProtocolPort);
        clients.add(client);
    }


    public static void main(String[] args) {
        logger.info("Starting app with args={}", Arrays.asList(args));

        CLIParser parser = new CLIParser("myshelfie");

        Namespace result = parser.parse(args);
        logger.info("Arguments successfully parsed");

        Boolean isVerbose = result.get(VERBOSE);
        boolean areArgumentsExhaustive = parser.areArgumentsExhaustive(result);

        if (areArgumentsExhaustive) {
            logger.info("CLI arguments are exhaustive, launching");
            // we read the values and assemble the configuration
            AppLaunchTarget target = result.get(TARGET);
            String serverHost = result.get(SERVER_IP);
            int serverTcpPort = result.get(SERVER_TCP_PORT);
            int serverRmiPort = result.get(SERVER_RMI_PORT);
            ClientUiMode modePreselection = result.get(CLIENT_MODE);
            ClientProtocol protocolPreselection = result.get(CLIENT_PROTOCOL);

            var clientConfig = new ClientExhaustiveConfiguration(modePreselection, protocolPreselection);
            var finalConfig = new ExhaustiveLaunchConfiguration(target, serverHost, serverTcpPort, serverRmiPort, List.of(clientConfig));

            launchConfiguration(finalConfig);
        } else {
            logger.info("CLI arguments are now exhaustive, launching wizard");
            // start wizard
            ExhaustiveLaunchConfiguration finalConfig = launchWizardForConfig(result);
            launchConfiguration(finalConfig);
        }
    }

    /**
     * Launches a CLI interface for retrieving the missing parameters
     */
    private static @NotNull ExhaustiveLaunchConfiguration launchWizardForConfig(Namespace nonExhaustivePresetConfig) {
        // TODO wizard
        ExhaustiveLaunchConfiguration config = Wizard.launchWizardAndAcquireParams(nonExhaustivePresetConfig);
        logger.info("wizard returned configuration {}", config);

        return config;
    }

    /**
     * Starts the
     */
    private static void launchConfiguration(@NotNull ExhaustiveLaunchConfiguration config) {
        switch (config.appLaunchTarget()) {
            case SERVER -> startServer(config);
            case CLIENT -> startClient(config);
            case SERVER_AND_CLIENT -> {
                startServer(config);
                startClient(config);
            }
        }
    }

}
