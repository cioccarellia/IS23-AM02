package it.polimi.ingsw.launcher.parameters;

import java.util.List;

public record ExhaustiveLaunchConfiguration(
        AppLaunchTarget appLaunchTarget,
        String serverIHost,
        int serverPort,
        List<ClientExhaustiveConfiguration> clientConfigurations
) {
}