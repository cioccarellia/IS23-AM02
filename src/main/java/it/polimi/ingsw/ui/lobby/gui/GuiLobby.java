package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class GuiLobby extends Application implements LobbyGateway {

    private final LobbyViewEventHandler handler;

    private boolean isKilled = false;

    private ServerStatus currentState = null;
    private List<PlayerInfo> playerInfo = new ArrayList<>();

    /**
     * Keeps a reference to the owner (which can be null at the beginning) of this client instance.
     */
    private String owner = null;

    public GuiLobby(LobbyViewEventHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        if (isKilled)
            return;

        currentState = status;
        this.playerInfo = playerInfo;

        renderModelUpdate();
    }

    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> {
                switch (failure.error()) {
                    case GAME_ALREADY_STARTED -> {

                    }
                    case INVALID_USERNAME -> {

                    }
                }
            }
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
            }
        }

        renderModelUpdate();
    }

    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()) {
                    case ALREADY_CONNECTED_PLAYER -> {

                    }
                    case USERNAME_ALREADY_IN_USE -> {

                    }
                    case MAX_PLAYER_AMOUNT_EACHED -> {

                    }
                    case NO_GAME_TO_JOIN -> {

                    }
                    case GAME_ALREADY_STARTED -> {

                    }
                    case GAME_ALREADY_ENDED -> {

                    }
                    case INVALID_USERNAME -> {

                    }
                }

            }
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
            }
        }

        renderModelUpdate();
    }

    private void renderModelUpdate() {
        if (isKilled)
            return;
    }

    @Override
    public void kill() {
        isKilled = true;
    }

    /**
     * @param lobbyStage   the lobby stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage lobbyStage) throws Exception {
        lobbyStage.setMaximized(true);
        lobbyStage.setFullScreen(false);
        lobbyStage.setFullScreenExitHint("");


        // Load root layout from fxml file.

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/loginScene.fxml"));
        Parent rootLayout = null;

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            //AppClient.LOGGER.severe(e.getMessage());
            System.exit(1);
        }


        lobbyStage.setScene(rootLayout.getScene());
        lobbyStage.setTitle("LOGIN PAGE");
        lobbyStage.show();

    }
}
