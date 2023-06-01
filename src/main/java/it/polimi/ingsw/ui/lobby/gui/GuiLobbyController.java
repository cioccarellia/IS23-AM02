package it.polimi.ingsw.ui.lobby.gui;

import it.polimi.ingsw.app.model.PlayerInfo;
import it.polimi.ingsw.controller.server.connection.ConnectionStatus;
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
import javafx.fxml.Initializable;
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

        currentState = status;
        this.playerInfo = playerInfo;

        renderModelUpdate();
        renderUserInfoTable();
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

            statusTextLabel.setText("Logged in as %s, waiting for game start".formatted(owner));

            renderUserInfoTable();
        } else {
            enableView(preLoginVBox);
            disableView(postLoginVBox);


            switch (currentState) {
                case NO_GAME_STARTED -> {
                    statusTextLabel.setText("Pick username and select game mode to create the game");
                    radioButtonVBox.setVisible(true);

                    actionButton.setDisable(false);
                    actionButton.setText("CREATE GAME");
                }
                case GAME_INITIALIZING -> {
                    radioButtonVBox.setVisible(false);
                    statusTextLabel.setText("Pick username to connect to the game");

                    actionButton.setDisable(false);
                    actionButton.setText("CONNECT");
                }
                case GAME_RUNNING -> {
                    statusTextLabel.setText("Game already running, change server");

                    actionButton.setText("CONNECT");
                    actionButton.setDisable(true);
                }
                case GAME_OVER -> {
                    statusTextLabel.setText("Game over");

                    actionButton.setText("CONNECT");
                    actionButton.setDisable(true);
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
                handler.sendGameStartRequest(usernameTextField.getText(), currentlySelectedGameMode);

                statusTextLabel.setText("Sending game start request...");

                actionButton.setText("WAIT");
                actionButton.setDisable(true);
            }

            case GAME_INITIALIZING -> {
                handler.sendGameConnectionRequest(usernameTextField.getText());

                statusTextLabel.setText("Sending connection request");

                actionButton.setText("WAIT");
                actionButton.setDisable(true);
            }

            case GAME_RUNNING -> {
                // nop
            }
        }
    }

    private void renderUserInfoTable() {
        setUsername(playerInfo.get(0).username(), HostUsername);
        setConnectionStatus(playerInfo.get(0).status(), HostConnectionStatus);


        switch (playerInfo.size()) {
            case 1 -> {

                turnLabeltoInvible(player1Role, player1Username, player1ConnectionStatus);

                turnLabeltoInvible(player2Role, player2Username, player2ConnectionStatus);

                turnLabeltoInvible(player3Role, player3Username, player3ConnectionStatus);

            }
            case 2 -> {

                turnLabeltoVisible(player1Role, player1Username, player1ConnectionStatus);
                setUsername(playerInfo.get(1).username(), player1Username);
                setConnectionStatus(playerInfo.get(1).status(), player1ConnectionStatus);

                turnLabeltoInvible(player2Role, player2Username, player2ConnectionStatus);

                turnLabeltoInvible(player3Role, player3Username, player3ConnectionStatus);
            }
            case 3 -> {

                turnLabeltoVisible(player1Role, player1Username, player1ConnectionStatus);
                setUsername(playerInfo.get(1).username(), player1Username);
                setConnectionStatus(playerInfo.get(1).status(), player1ConnectionStatus);

                turnLabeltoVisible(player2Role, player2Username, player2ConnectionStatus);
                setUsername(playerInfo.get(2).username(), player2Username);
                setConnectionStatus(playerInfo.get(2).status(), player2ConnectionStatus);

                turnLabeltoInvible(player3Role, player3Username, player3ConnectionStatus);

            }
            case 4 -> {

                turnLabeltoVisible(player1Role, player1Username, player1ConnectionStatus);
                setUsername(playerInfo.get(1).username(), player1Username);
                setConnectionStatus(playerInfo.get(1).status(), player1ConnectionStatus);

                turnLabeltoVisible(player2Role, player2Username, player2ConnectionStatus);
                setUsername(playerInfo.get(2).username(), player2Username);
                setConnectionStatus(playerInfo.get(2).status(), player2ConnectionStatus);

                turnLabeltoVisible(player3Role, player3Username, player3ConnectionStatus);
                setUsername(playerInfo.get(3).username(), player3Username);
                setConnectionStatus(playerInfo.get(3).status(), player3ConnectionStatus);

            }
        }
    }

    /**
     * set the text of the given label to the given connection status
     *
     * @param status
     * @param label
     */
    private void setConnectionStatus(ConnectionStatus status, Label label) {

        switch (status) {

            case OPEN -> {
                label.setText("Connected");
            }
            case DISCONNECTED -> {
                label.setText("Temporary Disconnected");

            }
            case CLOSED -> {
                label.setText("Disconnected");

            }
        }
    }

    /**
     * set the text of the given label to the gaven username
     *
     * @param username
     * @param label
     */
    public void setUsername(String username, Label label) {

        label.setText(username);

    }

    /**
     * set the visibility of the given label to false
     *
     * @param role
     * @param username
     * @param connectionStatus
     */
    public void turnLabeltoInvible(Label role, Label username, Label connectionStatus) {

        role.setVisible(false);
        username.setVisible(false);
        connectionStatus.setVisible(false);

    }

    /**
     * set the visibility to the given label to true
     *
     * @param role
     * @param username
     * @param connectionStatus
     */
    public void turnLabeltoVisible(Label role, Label username, Label connectionStatus) {

        role.setVisible(true);
        username.setVisible(true);
        connectionStatus.setVisible(true);

    }

    /**
     * Marks the controller as killed.
     */
    @Override
    public void kill() {
        isKilled = true;
    }

}
