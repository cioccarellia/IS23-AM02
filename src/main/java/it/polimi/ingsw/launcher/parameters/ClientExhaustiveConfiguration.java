package it.polimi.ingsw.launcher.parameters;

public record ClientExhaustiveConfiguration(
        ClientMode mode,
        ClientProtocol protocol
) {
}