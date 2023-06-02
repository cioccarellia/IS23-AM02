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
import it.polimi.ingsw.ui.lobby.LobbyGateway;
import it.polimi.ingsw.ui.lobby.LobbyViewEventHandler;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.model.game.GameMode.*;


/**
 * The controller class for the GUI lobby.
 * It manages the user interface elements and communicates with the server through the LobbyGateway interface.
 * Keeps a reference to the owner (which can be null at the beginning) of this client instance.
 */
public class GuiLobbyController implements LobbyGateway, Initializable {

    private LobbyViewEventHandler handler;

    // controller data
    private String owner = null;
    private boolean isKilled = false;


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
    public Label HostUsername;
    @FXML
    public Label HostConnectionStatus;
    @FXML
    public Label player1Username;
    @FXML
    public Label player2Username;
    @FXML
    public Label player3Username;
    @FXML
    public Label player1ConnectionStatus;
    @FXML
    public Label player2ConnectionStatus;
    @FXML
    public Label player3ConnectionStatus;
    @FXML
    public Label player1Role;
    @FXML
    public Label player2Role;
    @FXML
    public Label player3Role;

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


        renderModelUpdate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        this.currentState = status;
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
                        statusTextLabel.setText("A game has been initialized already. You can't choose the number of players.");

                        handler.sendStatusUpdateRequest();
                    }
                    case GAME_ALREADY_RUNNING -> {
                        statusTextLabel.setText("A game is already running, you can't enter. Change server if you want to play.\n");
                    }
                    case INVALID_USERNAME -> {
                        statusTextLabel.setText("The username is invalid.");
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
                    case ALREADY_CONNECTED_PLAYER -> {
                        statusTextLabel.setText("You are already connected to this game.");
                    }
                    case USERNAME_ALREADY_IN_USE -> {
                        statusTextLabel.setText("This username is already taken.");
                    }
                    case MAX_PLAYER_AMOUNT_EACHED -> {
                        statusTextLabel.setText("The maximum amount of players for this game has been reached already.");
                    }
                    case NO_GAME_TO_JOIN -> {
                        statusTextLabel.setText("No game has been started.");
                    }
                    case GAME_ALREADY_STARTED -> {
                        statusTextLabel.setText("A game is already running, you can't enter. Change server if you want to play.");
                    }
                    case GAME_ALREADY_ENDED -> {
                        statusTextLabel.setText("The game has already ended.");
                    }
                    case INVALID_USERNAME -> {
                        statusTextLabel.setText("The username is invalid.");
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

    /**
     * Renders the UI based on the current server status.
     */
    private void renderModelUpdate() {
        if (isKilled) {
            statusTextLabel.setText("Game killed");
            return;
        }

        if (currentState == null) {
            statusTextLabel.setText("Fetching status from server...");

            actionButton.setText("WAIT");
            actionButton.setDisable(true);
            return;
        }

        if (owner != null) {
            disableView(preLoginVBox);
            enableView(postLoginVBox);

            statusTextLabel.setText("Logged in as @%s, waiting for game start".formatted(owner));

            renderUserInfoTable();
        } else {
            enableView(preLoginVBox);
            disableView(postLoginVBox);

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

    private void enableView(VBox vbox) {
        vbox.setManaged(true);
        vbox.setVisible(true);
    }


    private void disableView(VBox vbox) {
        vbox.setManaged(false);
        vbox.setVisible(false);
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
        Node[][] gridPaneNodes = new Node[5][3];
        for (Node child : playerListTableGridPane.getChildren()) {
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);

            if (column != null && row != null) {
                gridPaneNodes[row][column] = child;
            }
        }


        for (int i = 0; i < 4; i++) {
            int rowIndex = i + 1;
            Label roleLabel = (Label) gridPaneNodes[rowIndex][0];
            Label usernameLabel = (Label) gridPaneNodes[rowIndex][1];
            Label statusLabel = (Label) gridPaneNodes[rowIndex][2];

            try {
                PlayerInfo player = playerInfo.get(i);

                setVisible(true, roleLabel, usernameLabel, statusLabel);

                roleLabel.setText(player.isHost() ? "Host" : "Player");
                usernameLabel.setText(player.username());
                statusLabel.setText(getConnectionStatusHumanReadable(player.status()));
            } catch (NullPointerException | IndexOutOfBoundsException ignored) {
                setVisible(false, roleLabel, usernameLabel, statusLabel);
            }
        }

    }

    private String getConnectionStatusHumanReadable(ConnectionStatus status) {
        switch (status) {
            case OPEN -> {
                return "Connected";
            }
            case DISCONNECTED -> {
                return "Disconnected";
            }
            case CLOSED -> {
                return "Quit";
            }
            default -> throw new IllegalStateException("Unexpected value: " + status);
        }
    }

    public void setVisible(boolean isVisible, Node... role) {
        Arrays.stream(role).forEach(it -> it.setVisible(isVisible));
    }

    /**
     * Marks the controller as killed.
     */
    @Override
    public void kill() {
        isKilled = true;
    }

}
