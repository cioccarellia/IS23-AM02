package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
import it.polimi.ingsw.controller.server.model.ServerStatus;
import it.polimi.ingsw.controller.server.result.TypedResult;
import it.polimi.ingsw.controller.server.result.failures.GameConnectionError;
import it.polimi.ingsw.controller.server.result.failures.GameCreationError;
import it.polimi.ingsw.controller.server.result.types.GameConnectionSuccess;
import it.polimi.ingsw.controller.server.result.types.GameCreationSuccess;
import it.polimi.ingsw.controller.server.validator.Validator;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.ui.Renderable;
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
import it.polimi.ingsw.utils.javafx.PaneViewUtil;
import it.polimi.ingsw.utils.javafx.UiUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.model.game.GameMode.*;


/**
 * The controller class for the GUI lobby.
 * It manages the user interface elements and communicates with the server through the LobbyGateway interface.
 * Keeps a reference to the owner (which can be null at the beginning) of this client instance.
 */
public class GuiLobbyController implements LobbyGateway, Initializable, Renderable {

    public static final int ROWS = 5;
    public static final int COLUMNS = 3;
    private LobbyViewEventHandler handler;

    // controller data
    private String owner = null;
    private boolean inop = false;


    // model game data
    private ServerStatus currentState = null;
    private List<PlayerInfo> playerInfo = new ArrayList<>();


    // FXML-bound views
    @FXML
    public Label statusTextLabel;


    @FXML
    public VBox preLoginVBox;


    @FXML
    public TextField usernameTextField;

    @FXML
    public VBox radioButtonVBox;

    @FXML
    public RadioButton twoPlayersRadioButton;
    @FXML
    public RadioButton threePlayersRadioButton;
    @FXML
    public RadioButton fourPlayersRadioButton;

    @FXML
    public VBox postLoginVBox;

    @FXML
    public GridPane playerListTableGridPane;


    @FXML
    public Button actionButton;


    // UI status model data
    private GameMode currentlySelectedGameMode;


    /**
     * Initializes the controller with the given LobbyViewEventHandler.
     *
     * @param handler The event handler for the lobby view.
     */
    public void injectEventHandler(LobbyViewEventHandler handler) {
        this.handler = handler;

        render();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRadioButtonsClickListeners();

        render();
    }

    /**
     * Updates the UI with the current server status and player information.
     */
    @Override
    public void onServerStatusUpdate(ServerStatus status, List<PlayerInfo> playerInfo) {
        if (inop)
            return;

        this.currentState = status;
        this.playerInfo = playerInfo;

        render();
    }

    /**
     * Handles the server reply for game creation request.
     */
    @Override
    public void onServerCreationReply(TypedResult<GameCreationSuccess, GameCreationError> result) {
        if (inop)
            return;

        switch (result) {
            case TypedResult.Failure<GameCreationSuccess, GameCreationError> failure -> {
                switch (failure.error()) {
                    case GAME_ALREADY_INITIALIZING -> {
                        statusTextLabel.setText("A game has been initialized already. You can't choose the number of players.");

                        handler.sendStatusUpdateRequest();
                    }
                    case GAME_ALREADY_RUNNING ->
                            statusTextLabel.setText("A game is already running, you can't enter. Change server if you want to play.\n");
                    case INVALID_USERNAME -> {
                        statusTextLabel.setText("The username is invalid.");
                    }
                }
            }
            case TypedResult.Success<GameCreationSuccess, GameCreationError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
                render();
            }
        }
    }

    /**
     * Handles the server reply for game connection request.
     */
    @Override
    public void onServerConnectionReply(TypedResult<GameConnectionSuccess, GameConnectionError> result) {
        if (inop)
            return;

        switch (result) {
            case TypedResult.Failure<GameConnectionSuccess, GameConnectionError> failure -> {
                switch (failure.error()) {
                    case ALREADY_CONNECTED_PLAYER -> statusTextLabel.setText("You are already connected to this game.");
                    case USERNAME_ALREADY_IN_USE -> statusTextLabel.setText("This username is already taken.");
                    case MAX_PLAYER_AMOUNT_EACHED ->
                            statusTextLabel.setText("The maximum amount of players for this game has been reached already.");
                    case NO_GAME_TO_JOIN -> statusTextLabel.setText("No game has been started.");
                    case GAME_ALREADY_STARTED ->
                            statusTextLabel.setText("A game is already running, you can't enter. Change server if you want to play.");
                    case GAME_ALREADY_ENDED -> statusTextLabel.setText("The game has already ended.");
                    case INVALID_USERNAME -> statusTextLabel.setText("The username is invalid.");
                }
            }
            case TypedResult.Success<GameConnectionSuccess, GameConnectionError> success -> {
                owner = success.value().username();
                playerInfo = success.value().playerInfo();
            }
        }

        render();
    }


    /**
     * Marks the controller as killed.
     */
    @Override
    public void inop() {
        inop = true;
    }

    /**
     * Renders the UI based on the current server status.
     */
    @Override
    public void render() {
        if (inop) {
            statusTextLabel.setText("Game inoperative");
            return;
        }

        if (currentState == null) {
            statusTextLabel.setText("Fetching status from server...");

            actionButton.setText("WAIT");
            actionButton.setDisable(true);
            return;
        }

        if (owner != null) {
            UiUtils.invisibleAndUnmanage(preLoginVBox);
            UiUtils.visibleAndManage(postLoginVBox);

            statusTextLabel.setText("Logged in as @%s, waiting for game start".formatted(owner));

            renderUserInfoTable();
        } else {
            UiUtils.visibleAndManage(preLoginVBox);
            UiUtils.invisibleAndUnmanage(postLoginVBox);

            switch (currentState) {
                case NO_GAME_STARTED -> {
                    statusTextLabel.setText("Pick username and select game mode to create the game");
                    radioButtonVBox.setVisible(true);

                    actionButton.setDisable(false);
                    usernameTextField.setDisable(false);
                    actionButton.setText("CREATE GAME");
                }
                case GAME_INITIALIZING -> {
                    radioButtonVBox.setVisible(false);
                    statusTextLabel.setText("Pick username to connect to the game");

                    actionButton.setDisable(false);
                    usernameTextField.setDisable(false);
                    actionButton.setText("CONNECT");
                }
                case GAME_RUNNING -> {
                    statusTextLabel.setText("Game already running, change server");

                    usernameTextField.setDisable(true);
                    radioButtonVBox.setVisible(false);

                    actionButton.setText("CONNECT");
                    actionButton.setDisable(true);
                }
                case GAME_OVER -> {
                    statusTextLabel.setText("Game over");

                    actionButton.setText("CONNECT");
                    usernameTextField.setDisable(true);
                    actionButton.setDisable(true);

                    radioButtonVBox.setVisible(false);

                    twoPlayersRadioButton.setDisable(true);
                    threePlayersRadioButton.setDisable(true);
                    fourPlayersRadioButton.setDisable(true);
                }
            }
        }
    }


    /**
     * Sets the click listeners for the radio buttons.
     */
    private void setRadioButtonsClickListeners() {
        twoPlayersRadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedGameMode = GAME_MODE_2_PLAYERS);
        threePlayersRadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedGameMode = GAME_MODE_3_PLAYERS);
        fourPlayersRadioButton.setOnMouseClicked(mouseEvent -> currentlySelectedGameMode = GAME_MODE_4_PLAYERS);
    }


    @FXML
    public void onActionButtonClicked() {
        if (currentState == null) {
            return;
        }

        switch (currentState) {
            case NO_GAME_STARTED -> {
                String username = usernameTextField.getText();

                if (Validator.isValidUsername(username)) {
                    statusTextLabel.setText("Sending game start request...");

                    actionButton.setText("WAIT");
                    actionButton.setDisable(true);

                    handler.sendGameStartRequest(username, currentlySelectedGameMode);
                } else {
                    statusTextLabel.setText("Invalid username [%s], try again".formatted(username));

                    actionButton.setText("START");
                    actionButton.setDisable(false);
                }
            }

            case GAME_INITIALIZING -> {
                String username = usernameTextField.getText();

                if (Validator.isValidUsername(username)) {
                    statusTextLabel.setText("Sending game connection request...");

                    actionButton.setText("WAIT");
                    actionButton.setDisable(true);

                    handler.sendGameConnectionRequest(usernameTextField.getText());
                } else {
                    statusTextLabel.setText("Invalid username [%s], try again".formatted(username));

                    actionButton.setText("CONNECT");
                    actionButton.setDisable(false);
                }
            }

            case GAME_RUNNING -> {
                // nop
            }
        }
    }

    /**
     * Renders the UI based on the current number of connected players.
     */
    private void renderUserInfoTable() {
        // matrix-ify nodes
        Node[][] gridPaneNodes = PaneViewUtil.matrixify(playerListTableGridPane, ROWS, COLUMNS);

        for (int i = 0; i < 4; i++) {
            int rowIndex = i + 1;

            Label roleLabel = (Label) gridPaneNodes[rowIndex][0];
            Label usernameLabel = (Label) gridPaneNodes[rowIndex][1];
            Label statusLabel = (Label) gridPaneNodes[rowIndex][2];

            try {
                PlayerInfo player = playerInfo.get(i);
                UiUtils.setVisible(true, roleLabel, usernameLabel, statusLabel);

                String roleText = player.isHost() ? "Host" : "Player";
                String statusText = getConnectionStatusHumanReadable(player.status());

                roleLabel.setText(roleText);
                usernameLabel.setText(player.username());
                statusLabel.setText(statusText);
            } catch (NullPointerException | IndexOutOfBoundsException ignored) {
                UiUtils.setVisible(false, roleLabel, usernameLabel, statusLabel);
            }
        }

    }

    private String getConnectionStatusHumanReadable(ConnectionStatus status) {
        return switch (status) {
            case OPEN -> "Connected";
            case DISCONNECTED -> "Disconnected";
            case CLOSED -> "Quit";
        };
    }
}
