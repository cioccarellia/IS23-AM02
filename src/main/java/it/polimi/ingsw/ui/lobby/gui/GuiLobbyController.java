package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.game.GameMode.*;


/**
 * The controller class for the GUI lobby.
 * It manages the user interface elements and communicates with the server through the LobbyGateway interface.
 * Keeps a reference to the owner (which can be null at the beginning) of this client instance.
 */
public class GuiLobbyController implements LobbyGateway {

    private LobbyViewEventHandler handler;

    private ServerStatus currentState = null;
    private List<PlayerInfo> playerInfo = new ArrayList<>();

    private String owner = null;

    private boolean isKilled = false;

    @FXML
    public TextField usernameField;
    @FXML
    public Label loginStatus;
    @FXML
    public Button loginButton;
    @FXML
    public RadioButton twoplayers;
    @FXML
    public RadioButton threeplayers;
    @FXML
    public RadioButton fourplayers;
    @FXML
    public Label nplayers;

    private GameMode gameMode;
    private String username;

    /**
     * Initializes the controller with the given LobbyViewEventHandler.
     *
     * @param handler The event handler for the lobby view.
     */
    public void init(LobbyViewEventHandler handler) {
        this.handler = handler;

        setRadioButtonsClickListeners();

        renderModelUpdate();
    }

    /**
     * Updates the UI with the current server status and player information.
     */
    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        if (isKilled)
            return;

        currentState = status;
        this.playerInfo = playerInfo;

        renderModelUpdate();
    }

    /**
     * Handles the server reply for game creation request.
     */
    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> {
                switch (failure.error()) {
                    case GAME_ALREADY_INITIALIZING -> {
                        loginStatus.setText("loginError: Game already initialized.");

                        handler.sendStatusUpdateRequest();
                    }
                    case GAME_ALREADY_RUNNING -> loginStatus.setText("loginError: Game running.\n");
                    case INVALID_USERNAME -> {
                        loginStatus.setText("loginError: Invalid Username.\n");
                        renderModelUpdate();
                    }
                }
            }
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
                renderModelUpdate();
            }
        }
    }

    /**
     * Handles the server reply for game connection request.
     */
    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        if (isKilled)
            return;

        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()) {
                    case ALREADY_CONNECTED_PLAYER -> loginStatus.setText("loginError: You are already connected to this game");
                    case USERNAME_ALREADY_IN_USE -> {
                        loginStatus.setText("loginError: Username already in use");
                        loginButton.setOnMouseClicked(mouseEvent -> handler.sendGameConnectionRequest(username));

                    }
                    case MAX_PLAYER_AMOUNT_EACHED -> {
                        loginStatus.setText("loginError: max player amount reached");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> kill());
                    }
                    case NO_GAME_TO_JOIN -> {
                        loginStatus.setText("loginError: no game to join");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> kill());
                    }
                    case GAME_ALREADY_STARTED -> {
                        loginStatus.setText("loginError: game already started");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> kill());
                    }
                    case GAME_ALREADY_ENDED -> {
                        loginStatus.setText("loginError: game already ended");
                        loginButton.setText("QUIT");
                        loginButton.setOnMouseClicked(mouseEvent -> kill());
                    }
                    case INVALID_USERNAME -> {
                        loginStatus.setText("loginError: invalid username");
                        loginButton.setOnMouseClicked(mouseEvent -> handler.sendGameConnectionRequest(username));
                    }
                }

            }
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();

                renderModelUpdate();
            }
        }

    }

    /**
     * Renders the UI based on the current server status.
     */
    private void renderModelUpdate() {
        if (isKilled) {
            loginStatus.setText("Game killed");
            return;
        }

        if (currentState == null) {
            loginStatus.setText("Fetching status from server...");
            return;
        }

        if (owner != null) {
            // mostrare la lista di giocatori al momento presenti
        }

        switch (currentState) {
            case NO_GAME_STARTED -> loginStatus.setText("Pick username and select game mode to create the game");
            case GAME_INITIALIZING -> {
                nplayers.setOpacity(0);

                // removes radio buttons from view
                twoplayers.setDisable(true);
                twoplayers.setOpacity(0);

                threeplayers.setDisable(true);
                threeplayers.setOpacity(0);

                fourplayers.setDisable(true);
                fourplayers.setOpacity(0);

                loginStatus.setText("Pick username to connect");
            }
            case GAME_RUNNING -> loginStatus.setText("Game already running");
            case GAME_OVER -> loginStatus.setText("Game over");
        }
    }

    /**
     * Sets the click listeners for the radio buttons.
     */
    private void setRadioButtonsClickListeners() {
        twoplayers.setOnMouseClicked(mouseEvent -> gameMode = GAME_MODE_2_PLAYERS);
        threeplayers.setOnMouseClicked(mouseEvent -> gameMode = GAME_MODE_3_PLAYERS);
        threeplayers.setOnMouseClicked(mouseEvent -> gameMode = GAME_MODE_4_PLAYERS);
    }

    /**
     * Marks the controller as killed.
     */
    @Override
    public void kill() {
        isKilled = true;
    }

    /**
     * Handles the submit button click event.
     */
    @FXML
    public void onSubmitButtonClick() {
        if (currentState == null) {
            return;
        }

        switch (currentState) {
            case NO_GAME_STARTED -> handler.sendGameStartRequest(usernameField.getText(), gameMode);

            case GAME_INITIALIZING -> handler.sendGameConnectionRequest(usernameField.getText());

            case GAME_RUNNING -> {
                // no
            }
        }
    }

}
