package it.polimi.ingsw.launcher.parameters;

import java.io.Serializable;

public record ClientExhaustiveConfiguration(
        ClientUiMode mode,
        ClientProtocol protocol
) implements Serializable {
}