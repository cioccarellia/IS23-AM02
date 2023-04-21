package it.polimi.ingsw.launcher.parameters;

public record ClientExhaustiveConfiguration(
        String username,
        ClientMode mode,
        ClientProtocol protocol
) {
}