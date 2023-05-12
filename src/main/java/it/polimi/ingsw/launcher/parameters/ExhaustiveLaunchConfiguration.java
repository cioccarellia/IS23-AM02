package it.polimi.ingsw.launcher.parameters;

import java.util.List;

public record ExhaustiveLaunchConfiguration(
        AppLaunchTarget appLaunchTarget,
        String serverHost,
        int serverTcpPort,
        int serverRmiPort,

        // multi-client launch support
        List<ClientExhaustiveConfiguration> clientConfigurations
) {
}